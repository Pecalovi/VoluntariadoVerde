package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
				user, pass);
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

		Usuario sesionUsuario = null;

		try {
			AccesoBD bd = new AccesoBD();

			// =========================
			// 1. BUSCAR EN VOLUNTARIOS
			// =========================
			String sqlVol = "SELECT * FROM voluntarios WHERE LOWER(TRIM(email)) = ? AND pass = ?";
			PreparedStatement psVol = bd.con.prepareStatement(sqlVol);

			psVol.setString(1, email.toLowerCase().trim());
			psVol.setString(2, pass);

			ResultSet rsVol = psVol.executeQuery();

			if (rsVol.next()) {

				int id = rsVol.getInt("id_voluntario");
				String nombre = rsVol.getString("nombre");
				String apellidos = rsVol.getString("apellidos");
				String telefono = rsVol.getString("telefono");
				Date fechaNacDB = rsVol.getDate("fechaNac");
				boolean vehiculo = rsVol.getBoolean("vehiculo");
				int discapacidad = rsVol.getInt("discapacidad");

				LocalDate fechaNac = null;
				if (fechaNacDB != null) {
					fechaNac = fechaNacDB.toLocalDate();
				}

				sesionUsuario = new Voluntario(nombre, apellidos, id, telefono, email, pass, discapacidad, vehiculo,
						fechaNac);

				rsVol.close();
				psVol.close();
				bd.disconnect();
				return sesionUsuario;
			}

			rsVol.close();
			psVol.close();

			// =========================
			// 2. BUSCAR EN ORGANIZADORES
			// =========================
			String sqlOrg = "SELECT * FROM organizadores WHERE LOWER(TRIM(email)) = ? AND pass = ?";
			PreparedStatement psOrg = bd.con.prepareStatement(sqlOrg);

			psOrg.setString(1, email.toLowerCase().trim());
			psOrg.setString(2, pass);

			ResultSet rsOrg = psOrg.executeQuery();

			if (rsOrg.next()) {

				int id = rsOrg.getInt("id_organizador");
				String nombre = rsOrg.getString("nombre");
				String apellidos = rsOrg.getString("apellidos");
				String telefono = rsOrg.getString("telefono");
				String empresa = rsOrg.getString("empresa");

				sesionUsuario = new Organizador(nombre, apellidos, id, telefono, email, pass, empresa);
			}

			rsOrg.close();
			psOrg.close();
			bd.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sesionUsuario;
	}

	public boolean registrar(Usuario u) {

		try {

			// =========================
			// VOLUNTARIO
			// =========================
			if (u instanceof Voluntario) {

				Voluntario v = (Voluntario) u;

				String sql = "INSERT INTO voluntarios (email, pass, nombre, apellidos, fechaNac, telefono, vehiculo, discapacidad) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

				PreparedStatement ps = con.prepareStatement(sql);

				ps.setString(1, v.getEmail());
				ps.setString(2, v.getPass());
				ps.setString(3, v.getNombre());
				ps.setString(4, v.getApellidos());
				ps.setDate(5, java.sql.Date.valueOf(v.getFechaNac()));
				ps.setString(6, v.getNumTelf());
				ps.setBoolean(7, v.getVehiculo());
				ps.setInt(8, v.getDiscapacidad());

				ps.executeUpdate();
				ps.close();

				return true;
			}

			// =========================
			// ORGANIZADOR
			// =========================
			else if (u instanceof Organizador) {

				Organizador o = (Organizador) u;

				String sql = "INSERT INTO organizadores (email, pass, nombre, apellidos, telefono, empresa) "
						+ "VALUES (?, ?, ?, ?, ?, ?)";

				PreparedStatement ps = con.prepareStatement(sql);

				ps.setString(1, o.getEmail());
				ps.setString(2, o.getPass());
				ps.setString(3, o.getNombre());
				ps.setString(4, o.getApellidos());
				ps.setString(5, o.getNumTelf());
				ps.setString(6, o.getEntidad());

				ps.executeUpdate();
				ps.close();

				return true;
			}

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

	public static ArrayList<Evento> obtenerEventos(String atributo, Object valor) {

		String sql = "SELECT * FROM eventos";
		ArrayList<Evento> eventos = new ArrayList<>();
		boolean filtrar = false;

		try {
			AccesoBD bd = new AccesoBD();

			if (atributo != null && !atributo.isEmpty() && valor != null) {

				switch (atributo) {
				case "nombre":
				case "lugar":
					sql += " WHERE " + atributo + " LIKE ?";
					filtrar = true;
					break;

				case "id_organizador":
				case "capacidad_maxima":
				case "id_evento":
				case "tipo":
					sql += " WHERE " + atributo + " = ?";
					filtrar = true;
					break;
				}
			}

			sql += " ORDER BY fecha";

			PreparedStatement ps = bd.con.prepareStatement(sql);

			if (filtrar) {

				if ("nombre".equals(atributo) || "lugar".equals(atributo)) {
					ps.setString(1, "%" + valor.toString() + "%");
				} else if (valor instanceof Integer) {
					ps.setInt(1, (Integer) valor);
				} else if (valor instanceof String) {
					ps.setString(1, (String) valor);
				} else {
					ps.setObject(1, valor);
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

				LocalDate fecha = (fechaDB != null) ? fechaDB.toLocalDate() : null;

				eventos.add(new Evento(nombreEventoDB, lugarDB, fecha, tipoDB, idEventoDB, descripcionDB,
						capacidadMaximaDB, 0, 0));
			}

			rs.close();
			ps.close();
			bd.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return eventos;
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

			ps.executeUpdate();

			ps.close();
			return true;

		} catch (Exception m) {
			m.printStackTrace();
			return false;
		}

	}

	public boolean inscribir(int idUser, int idEvento) {
		String sql = "INSERT INTO inscripciones (id_usuario, id_evento, estado) VALUES (?, ?, 'pendiente') "
				+ "ON DUPLICATE KEY UPDATE estado = 'pendiente'";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, idUser);
			ps.setInt(2, idEvento);

			ps.executeUpdate();
			ps.close();
			return true;
		} catch (Exception m) {
			System.err.println("Error en inscribir: " + m.getMessage());
			m.printStackTrace();
			return false;
		}
	}

	public boolean usuarioInscrito(int idUsuario, int idEvento) {
		String sql = "SELECT * FROM inscripciones WHERE id_usuario = ? AND id_evento = ? AND estado != 'cancelado'";

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
		ps.setInt(1, id_usuario);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			idsEventos.add(rs.getInt("id_evento"));
		}

		rs.close();
		ps.close();
		return idsEventos;
	}

	public void borrarDatosUsuario(int idUsuario) throws SQLException {
		String sql = "UPDATE usuarios SET nombre = 'Usuario Eliminado', apellidos = '', email = '', pass = '', fechaNac = NULL,\r\n"
				+ "telefono = 0, empresa = NULL, vehiculo = '', discapacidad = ''\r\n" + "WHERE id_usuario = ?;";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, idUsuario);
		ps.executeUpdate();
	}

	public boolean editarDatosUsuario(Usuario u) {

		try {

			// =========================
			// ORGANIZADOR
			// =========================
			if (u instanceof Organizador) {

				String sql = "UPDATE organizadores SET email=?, nombre=?, apellidos=?, telefono=?, empresa=? WHERE id_organizador=?";

				PreparedStatement ps = con.prepareStatement(sql);

				Organizador org = (Organizador) u;

				ps.setString(1, org.getEmail());
				ps.setString(2, org.getNombre());
				ps.setString(3, org.getApellidos());
				ps.setString(4, org.getNumTelf());
				ps.setString(5, org.getEntidad());
				ps.setInt(6, org.getId());

				ps.executeUpdate();
				ps.close();

				return true;
			}

			// =========================
			// VOLUNTARIO
			// =========================
			else if (u instanceof Voluntario) {

				String sql = "UPDATE voluntarios SET email=?, nombre=?, apellidos=?, telefono=?, vehiculo=?, discapacidad=?, fechaNac=? WHERE id_voluntario=?";

				PreparedStatement ps = con.prepareStatement(sql);

				Voluntario vol = (Voluntario) u;

				ps.setString(1, vol.getEmail());
				ps.setString(2, vol.getNombre());
				ps.setString(3, vol.getApellidos());
				ps.setString(4, vol.getNumTelf());

				ps.setBoolean(5, vol.getVehiculo());
				ps.setInt(6, vol.getDiscapacidad());

				if (vol.getFechaNac() != null) {
					ps.setDate(7, java.sql.Date.valueOf(vol.getFechaNac()));
				} else {
					ps.setDate(7, null);
				}

				ps.setInt(8, vol.getId());

				ps.executeUpdate();
				ps.close();

				return true;
			}

			return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static List<Inscripcion> obtenerVoluntarios(int idEvento) {

		String sql = "SELECT i.*, v.* " + "FROM inscripciones i "
				+ "JOIN voluntarios v ON i.id_voluntario = v.id_voluntario " + "WHERE i.id_evento = ?";

		List<Inscripcion> inscripciones = new ArrayList<>();

		try {
			AccesoBD bd = new AccesoBD();
			PreparedStatement ps = bd.con.prepareStatement(sql);

			ps.setInt(1, idEvento);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				// =========================
				// DATOS VOLUNTARIO
				// =========================
				int idVol = rs.getInt("id_voluntario");
				String nombre = rs.getString("nombre");
				String apellidos = rs.getString("apellidos");
				String email = rs.getString("email");
				String telefono = rs.getString("telefono");
				String pass = rs.getString("pass");

				int discapacidad = rs.getInt("discapacidad");
				boolean vehiculo = rs.getBoolean("vehiculo");

				Date fechaNacDB = rs.getDate("fechaNac");
				LocalDate fechaNac = null;
				if (fechaNacDB != null) {
					fechaNac = fechaNacDB.toLocalDate();
				}

				Voluntario v = new Voluntario(nombre, apellidos, idVol, telefono, email, pass, discapacidad, vehiculo,
						fechaNac);

				// =========================
				// DATOS INSCRIPCIÓN
				// =========================
				int idIns = rs.getInt("id_inscripcion");
				LocalDateTime fechaIns = rs.getTimestamp("fecha_inscripcion").toLocalDateTime();
				String estado = rs.getString("estado");
				Integer valoracion = rs.getObject("valoracion") != null ? rs.getInt("valoracion") : null;

				Inscripcion ins = new Inscripcion(idIns, v, idEvento, fechaIns, estado, valoracion);

				inscripciones.add(ins);
			}

			rs.close();
			ps.close();
			bd.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return inscripciones;
	}

	public void cambiarEstadoInscripcion(int idUsuario, int idEvento, String estado) {
		String sql = "UPDATE inscripciones SET estado = ? WHERE id_usuario = ? AND id_evento = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, estado);
			ps.setInt(2, idUsuario);
			ps.setInt(3, idEvento);

			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean cancelarInscripcion(int idUser, int idEvento) {
		String sql = "UPDATE inscripciones SET estado = ? WHERE id_usuario = ? AND id_evento = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "cancelado");
			ps.setInt(2, idUser);
			ps.setInt(3, idEvento);

			ps.executeUpdate();
			ps.close();
			return true;
		} catch (Exception e) {
			System.err.println("Error en cancelarInscripcion: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public static int contadorHome(String tabla, Integer idRol) {
		int numContador = 0;
		String sql;

		if (idRol != null) {
			sql = "SELECT COUNT(*) FROM " + tabla + " WHERE id_rol = ?";
		} else {
			sql = "SELECT COUNT(*) FROM " + tabla;
		}

		try {
			AccesoBD bd = new AccesoBD();
			PreparedStatement ps = bd.con.prepareStatement(sql);

			if (idRol != null) {
				ps.setInt(1, idRol);
			}

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				numContador = rs.getInt(1);
			}

			rs.close();
			ps.close();
			bd.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return numContador;
	}

	public static int contadorInscripciones() {
		int numContador = 0;
		String sql = "SELECT COUNT(*) FROM inscripciones WHERE estado != 'cancelado'";

		try {
			AccesoBD bd = new AccesoBD();
			PreparedStatement ps = bd.con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				numContador = rs.getInt(1);
			}

			rs.close();
			ps.close();
			bd.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return numContador;
	}

	// Método para listar voluntarios, organizadores y eventos según variable
	public ArrayList<Object> PanelAdmin(String tabla) {
		ArrayList<Object> lista = new ArrayList<>();
		String sql;
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql = "SELECT * FROM " + tabla;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			switch (tabla) {
			case "voluntarios":
				while (rs.next()) {
					int id = rs.getInt("id_voluntario");
					String nombre = rs.getString("nombre");
					String apellidos = rs.getString("apellidos");
					String telefono = rs.getString("telefono");
					String email = rs.getString("email");
					Date fechaNacDB = rs.getDate("fechaNac");
					boolean vehiculo = rs.getBoolean("vehiculo");
					int discapacidad = rs.getInt("discapacidad");

					LocalDate fechaNac = null;
					if (fechaNacDB != null) {
						fechaNac = fechaNacDB.toLocalDate();
					}

					Voluntario v = new Voluntario(nombre, apellidos, id, telefono, email, null, discapacidad, vehiculo,
							fechaNac);

					lista.add(v);

				}
				break;

			case "empresas":
				while (rs.next()) {
					Map<String, Object> fila = new HashMap<>();

					fila.put("empresa", rs.getString("empresa"));
					fila.put("total", rs.getInt("total_organizadores"));

					lista.add(fila);
				}
				break;

			case "eventos":
				while (rs.next()) {

					int idEventoDB = rs.getInt("id_evento");
					String nombreEventoDB = rs.getString("nombre");
					String tipoDB = rs.getString("tipo");
					String descripcionDB = rs.getString("descripcion");
					Date fechaDB = rs.getDate("fecha");
					String lugarDB = rs.getString("lugar");
					int capacidadMaximaDB = rs.getInt("capacidad_maxima");

					LocalDate fecha = (fechaDB != null) ? fechaDB.toLocalDate() : null;

					lista.add(new Evento(nombreEventoDB, lugarDB, fecha, tipoDB, idEventoDB, descripcionDB,
							capacidadMaximaDB, 0, 0));
				}
				break;

			default:
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return lista;
	}

	public boolean valorarVoluntario(int idInscripcion, int nota) {
		// Validar que la nota sea lógica (de 1 a 5)
		if (nota < 1 || nota > 5)
			return false;

		String sql = "UPDATE inscripciones SET valoracion = ? WHERE id_inscripcion = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, nota);
			ps.setInt(2, idInscripcion);

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}