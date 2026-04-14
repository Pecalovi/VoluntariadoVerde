package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccesoBD {
	public static final String DRIVER_MYSQL = "com.mysql.cj.jdbc.Driver";

	public String user, pass, server, db;
	public int port;
	Connection con = null;
	Statement st = null;

	public AccesoBD() throws ClassNotFoundException, SQLException {
		super();
		this.user = "vv";
		this.pass = "volver";
		this.server = "localhost";
		this.db = "voluntariado";
		this.port = 3306;
		connect();// Está dentro del constructor, asique cada vez que se instancie esta clase se
					// llamará a conectar
		System.out.println("Conexión establecida con " + db);
	}

	private void connect() throws ClassNotFoundException, SQLException {

		Class.forName(DRIVER_MYSQL);
		con = DriverManager.getConnection("jdbc:mysql://" + server + ":" + port + "/" + db + "?serverTimezone=UTC",
				user, pass);// ruta, usuario, contraseña
	}

	public void disconnect() {
		try {
			st.close();

		} catch (Exception e) {

		}
		try {
			con.close();
		} catch (Exception e) {

		}

	}

	public static Usuario iniciarSesion(String email, String pass) {

		String sql = "SELECT * FROM usuarios WHERE LOWER(TRIM(email)) = ? AND pass = ?";

		Usuario sesionUsuario = null;

		try {
			AccesoBD bd = new AccesoBD();
			PreparedStatement ps = bd.con.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, pass);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				int idDB = rs.getInt("id_usuario");
				String emailDB = rs.getString("email");
				String passDB = rs.getString("pass");
				String nombreDB = rs.getString("nombre");
				String apellidoDB = rs.getString("apellidos");
				Date fechaNacDB = rs.getDate("fechaNac");
				int telefonoDB = rs.getInt("telefono");
				String empresaDB = rs.getString("empresa");
				String vehiculoDB = rs.getString("vehiculo");
				String discapacidadDB = rs.getString("discapacidad");
				int idRolDB = rs.getInt("id_rol");

				if (idRolDB==1) {

					LocalDate fechaNac = null;
					if (fechaNacDB != null) {
						fechaNac = fechaNacDB.toLocalDate();
					}

					// Instanciar un voluntario
					sesionUsuario = new Voluntario(nombreDB, apellidoDB, idDB, telefonoDB, emailDB, passDB,
							discapacidadDB, vehiculoDB, fechaNac);

				} else if (idRolDB==2) {
					sesionUsuario = new Organizador(nombreDB, apellidoDB, idDB, telefonoDB, emailDB, passDB, empresaDB);

				}
			}

			rs.close();
			ps.close();
			bd.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sesionUsuario;
	}

	public boolean registrar(Usuario u) {
		//
		Date fechaNac = null;
		String empresa = null;
		String vehiculo = null;
		String discapacidad = null;
		int idRol = 0;

		if (u instanceof Voluntario) {
			Voluntario v = (Voluntario) u;
			vehiculo = v.getVehiculo();
			discapacidad = v.getDiscapacidad();

			fechaNac = java.sql.Date.valueOf(v.getFechaNac());

			idRol = 1;
		}

		if (u instanceof Organizador) {
			Organizador o = (Organizador) u;
			empresa = o.getEntidad();
			idRol = 2;
		}

		String sql = "INSERT INTO usuarios (email, pass, nombre, apellidos, fechaNac, telefono, empresa, vehiculo, discapacidad, id_rol) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {

			// 1. Insertar usuario (es id 1 el voluntario)
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, u.email);
			ps.setString(2, u.pass);
			ps.setString(3, u.nombre);
			ps.setString(4, u.apellidos);
			ps.setDate(5, fechaNac);
			ps.setLong(6, u.numTelf);
			ps.setString(7, empresa);
			ps.setString(8, vehiculo);
			ps.setString(9, discapacidad);
			ps.setInt(10, idRol);

			// Ejecutar consulta
			ps.executeUpdate();

			// Cerrar recursos
			ps.close();
			return true;
		} catch (SQLIntegrityConstraintViolationException e) {

			return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
	}

	public boolean crearEvento(Evento e) {

		Date fecha = java.sql.Date.valueOf(e.getFecha());

		String sql = "INSERT INTO eventos (nombre, tipo, fecha, lugar, capacidad_maxima, descripcion, id_organizador) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		try {

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, e.getNombre());
			ps.setString(2, e.getTipo());
			ps.setDate(3, fecha);
			ps.setString(4, e.getUbicacion());
			ps.setInt(5, e.getPlazasTotales());
			ps.setString(6, e.getDescripcion());
			ps.setInt(7, e.getId_usuario());

			// Ejecutar consulta
			ps.executeUpdate();

			// Cerrar recursos
			ps.close();
			return true;

		} catch (Exception m) {
			m.printStackTrace();
			return false;
		}
	}

	public static ArrayList<Evento> obtenerEventos(String atributo, String valor) {

		String sql = "SELECT * FROM eventos";
		Evento evento = null;
		ArrayList<Evento> eventos = new ArrayList<>();

		try {
			AccesoBD bd = new AccesoBD();
			if (atributo != null && !atributo.isEmpty() && valor != null && !valor.isEmpty()) {

				if (atributo.equals("nombre") || atributo.equals("tipo") || atributo.equals("lugar")) {

					if (atributo.equals("nombre") || atributo.equals("lugar")) {
						sql += " WHERE " + atributo + " LIKE ?";
					} else {
						sql += " WHERE " + atributo + " = ?";
					}
				}
			}

			sql += " ORDER BY fecha";

			PreparedStatement ps = bd.con.prepareStatement(sql);

			if (atributo != null && !atributo.isEmpty() && valor != null && !valor.isEmpty()) {

				if (atributo.equals("nombre") || atributo.equals("lugar")) {
					ps.setString(1, "%" + valor + "%");
				} else {
					ps.setString(1, valor);
				}
			}

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				int idEventoDB = rs.getInt("id_evento");
				String nombreEventoDB = rs.getString("nombre");
				String tipoDB = rs.getString("tipo");
				String descripcionDB = rs.getString("descripcion");
				Date fechaDB = rs.getDate("fecha");
				String lugarDB = rs.getString("lugar");
				int capacidadMaximaDB = rs.getInt("capacidad_maxima");

				LocalDate fecha = null;
				if (fechaDB != null) {
					fecha = fechaDB.toLocalDate();
				}

				// Instanciar el evento
				evento = new Evento(nombreEventoDB, lugarDB, fecha, tipoDB, idEventoDB, descripcionDB,
						capacidadMaximaDB, 0, 0);

				eventos.add(evento);
			}

			rs.close();
			ps.close();
			bd.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return eventos;
	}

	public static Evento obtenerEvento(int id) {

		String sql = "SELECT * FROM eventos_con_inscritos where id_evento= ?";

		Evento evento = null;

		try {
			AccesoBD bd = new AccesoBD();
			PreparedStatement ps = bd.con.prepareStatement(sql);

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				int idEventoDB = rs.getInt("id_evento");
				String nombreEventoDB = rs.getString("nombre");
				String tipoDB = rs.getString("tipo");
				String descripcionDB = rs.getString("descripcion");
				Date fechaDB = rs.getDate("fecha");
				String lugarDB = rs.getString("lugar");
				int capacidadMaximaDB = rs.getInt("capacidad_maxima");
				int inscritos = rs.getInt("total_inscritos");

				LocalDate fecha = null;
				if (fechaDB != null) {
					fecha = fechaDB.toLocalDate();
				}

				// Instanciar el evento
				evento = new Evento(nombreEventoDB, lugarDB, fecha, tipoDB, idEventoDB, descripcionDB,
						capacidadMaximaDB, 0, inscritos);

			}

			rs.close();
			ps.close();
			bd.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return evento;
	}

	public static List<Evento> obtenerEventosUsuario(int idUsuario) {
		String sql = "SELECT e.* " + "FROM eventos e " + "JOIN inscripciones i ON e.id_evento = i.id_evento "
				+ "WHERE i.id_usuario = ?";

		List<Evento> eventos = new ArrayList<>();

		try {
			AccesoBD bd = new AccesoBD();
			PreparedStatement ps = bd.con.prepareStatement(sql);
			ps.setInt(1, idUsuario);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Evento evento = new Evento(rs.getString("nombre"), rs.getString("lugar"),
						rs.getDate("fecha").toLocalDate(), rs.getString("tipo"), rs.getInt("id_evento"),
						rs.getString("descripcion"), rs.getInt("capacidad_maxima"), 0, 0);
				eventos.add(evento);
			}

			rs.close();
			ps.close();
			bd.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return eventos;
	}

	public static int obtenerUltimoEvento() {

		String sql = "SELECT id_evento FROM eventos ORDER BY id_evento DESC LIMIT 1";

		int idEventoDB = 0;
		try {
			AccesoBD bd = new AccesoBD();
			PreparedStatement ps = bd.con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				idEventoDB = rs.getInt("id_evento");
			}
			rs.close();
			ps.close();
			bd.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return idEventoDB;
	}

	public boolean insertarRecorrido(Puntos punto) {

		String sql = "INSERT INTO eventos_zonas (id_evento, id_zona, punto_kilometrico) VALUES (?, ?, ?)";

		try {

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, punto.getIdEvento());
			ps.setInt(2, punto.getTipoPunto());
			ps.setDouble(3, punto.getKmPunto());

			// Ejecutar consulta
			ps.executeUpdate();

			// Cerrar recursos
			ps.close();
			return true;

		} catch (Exception m) {
			m.printStackTrace();
			return false;
		}

	}

	public boolean inscribir(int idUser, int idEvento) {

		String sql = "INSERT INTO inscripciones (id_usuario, id_evento) VALUES ( ?, ?)";

		try {

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, idUser);
			ps.setInt(2, idEvento);

			// Ejecutar consulta
			ps.executeUpdate();

			// Cerrar recursos
			ps.close();
			return true;

		} catch (Exception m) {
			m.printStackTrace();
			return false;
		}

	}

	public boolean usuarioInscrito(int idUsuario, int idEvento) {

		String sql = "SELECT * FROM inscripciones WHERE id_usuario = ? AND id_evento = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, idUsuario);
			ps.setInt(2, idEvento);

			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		} catch (Exception m) {
			m.printStackTrace();
			return false;
		}
	}

	public ArrayList<Integer> obtenerInscripciones(int id_usuario) throws SQLException {
		String sql = "select * from inscripciones where id_usuario=?";
		ArrayList<Integer> idsEventos = new ArrayList<>();

		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		;
		ps.setInt(1, id_usuario);
		ps.executeQuery();

		if (rs.next()) {
			idsEventos.add(rs.getInt("id_evento"));
		}

		return idsEventos;

	}

	public void borrarDatosUsuario(int idUsuario) throws SQLException {
		String sql = "UPDATE usuarios SET nombre = '', apellidos = '', email = CONCAT('DeletedUser', id_usuario), pass = '', fechaNac = NULL,\r\n"
				+ "telefono = 0, empresa = NULL, vehiculo = '', discapacidad = ''\r\n" + "WHERE id_usuario = ?;";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, idUsuario);
		ps.executeUpdate();
	}

	public boolean editarDatosUsuario(Usuario u) {
	    try {

	        String sql = "UPDATE usuarios SET email=?, nombre=?, apellidos=?, telefono=? WHERE id_usuario=?";
	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setString(1, u.getEmail());
	        ps.setString(2, u.getNombre());
	        ps.setString(3, u.getApellidos());
	        ps.setInt(4, u.getNumTelf());
	        ps.setInt(5, u.getId());

	        ps.executeUpdate();
	        ps.close();

	        if (u instanceof Organizador) {

	            Organizador org = (Organizador) u;

	            String sqlOrg = "UPDATE usuarios SET empresa=? WHERE id_usuario=?";
	            PreparedStatement psOrg = con.prepareStatement(sqlOrg);

	            psOrg.setString(1, org.getEntidad());
	            psOrg.setInt(2, org.getId());

	            psOrg.executeUpdate();
	            psOrg.close();
	        }

	        else if (u instanceof Voluntario) {

	            Voluntario vol = (Voluntario) u;

	            String sqlVol = "UPDATE usuarios SET vehiculo=?, discapacidad=?, fechaNac=? WHERE id_usuario=?";
	            PreparedStatement psVol = con.prepareStatement(sqlVol);

	            psVol.setString(1, vol.getVehiculo());
	            psVol.setString(2, vol.getDiscapacidad());

	            if (vol.getFechaNac() != null) {
	                psVol.setDate(3, java.sql.Date.valueOf(vol.getFechaNac()));
	            } else {
	                psVol.setDate(3, null);
	            }

	            psVol.setInt(4, vol.getId());

	            psVol.executeUpdate();
	            psVol.close();
	        }

	        return true;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}
