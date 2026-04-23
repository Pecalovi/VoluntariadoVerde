package model;

import java.sql.Connection;
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

	private static Voluntario crearVoluntario(ResultSet rs) throws SQLException {

		LocalDate fechaNac = rs.getDate("fechaNac") != null ? rs.getDate("fechaNac").toLocalDate() : null;

		LocalDate fechaRegistro = rs.getDate("fecha_registro") != null ? rs.getDate("fecha_registro").toLocalDate()
				: null;

		double media = rs.getDouble("media");

		return new Voluntario(rs.getString("nombre"), rs.getString("apellidos"), rs.getInt("id_voluntario"),
				rs.getString("telefono"), rs.getString("email"), rs.getString("pass"), fechaRegistro,
				rs.getInt("discapacidad"), rs.getBoolean("vehiculo"), fechaNac, media);
	}

	private static Organizador crearOrganizador(ResultSet rs) throws SQLException {

		LocalDate fechaRegistro = rs.getDate("fecha_registro") != null ? rs.getDate("fecha_registro").toLocalDate()
				: null;

		return new Organizador(rs.getString("nombre"), rs.getString("apellidos"), rs.getInt("id_organizador"),
				rs.getString("telefono"), rs.getString("email"), rs.getString("pass"), fechaRegistro,
				rs.getString("empresa"));
	}

	private static Evento crearEvento(ResultSet rs) throws SQLException {

		LocalDate fechaInicio = rs.getDate("fecha_inicio") != null ? rs.getDate("fecha_inicio").toLocalDate() : null;

		LocalDate fechaFin = rs.getDate("fecha_fin") != null ? rs.getDate("fecha_fin").toLocalDate() : null;

		return new Evento(rs.getInt("id_evento"), rs.getString("nombre"), rs.getString("tipo"),
				rs.getString("descripcion"), fechaInicio, fechaFin, rs.getString("lugar"), rs.getString("edicion"),
				rs.getString("estado"), rs.getInt("id_organizador"));
	}

	public static Usuario iniciarSesion(String email, String pass) {

		Usuario sesionUsuario = null;

		try {
			AccesoBD bd = new AccesoBD();

			String emailLimpio = email.toLowerCase().trim();

			// =========================
			// 1. VOLUNTARIO
			// =========================
			String sqlVol = "SELECT * FROM voluntario_media WHERE LOWER(TRIM(email)) = ? AND pass = ?";
			PreparedStatement psVol = bd.con.prepareStatement(sqlVol);

			psVol.setString(1, emailLimpio);
			psVol.setString(2, pass);

			ResultSet rsVol = psVol.executeQuery();

			if (rsVol.next()) {
				sesionUsuario = crearVoluntario(rsVol);
			}

			rsVol.close();
			psVol.close();

			// Si ya encontró voluntario, no sigue
			if (sesionUsuario != null) {
				bd.disconnect();
				return sesionUsuario;
			}

			// =========================
			// 2. ORGANIZADOR
			// =========================
			String sqlOrg = "SELECT * FROM organizadores WHERE LOWER(TRIM(email)) = ? AND pass = ?";
			PreparedStatement psOrg = bd.con.prepareStatement(sqlOrg);

			psOrg.setString(1, emailLimpio);
			psOrg.setString(2, pass);

			ResultSet rsOrg = psOrg.executeQuery();

			if (rsOrg.next()) {
				sesionUsuario = crearOrganizador(rsOrg);
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

		String sql = "INSERT INTO eventos (nombre, tipo, descripcion, fecha_inicio, fecha_fin, lugar, edicion, estado, id_organizador) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, e.getNombre());
			ps.setString(2, e.getTipo());
			ps.setString(3, e.getDescripcion());
			ps.setDate(4, java.sql.Date.valueOf(e.getFecha_inicio()));
			if (e.getFecha_fin() != null) {
				ps.setDate(5, java.sql.Date.valueOf(e.getFecha_fin()));
			} else {
				ps.setNull(5, java.sql.Types.DATE);
			}
			ps.setString(6, e.getLugar());
			ps.setString(7, e.getEdicion());
			ps.setString(8, e.getEstado());
			ps.setInt(9, e.getId_organizador());

			ps.executeUpdate();
			ps.close();
			return true;

		} catch (Exception m) {
			m.printStackTrace();
			return false;
		}
	}

	public static ArrayList<Evento> obtenerEventos(String atributo, Object valor, boolean incluirFinalizados) {

		String sql = "SELECT * FROM eventos WHERE 1=1";
		ArrayList<Evento> eventos = new ArrayList<>();
		boolean filtrar = false;

		try {
			AccesoBD bd = new AccesoBD();

			// 🔹 Filtro estado opcional
			if (!incluirFinalizados) {
				sql += " AND estado <> 'Finalizado'";
			}

			if (atributo != null && !atributo.isEmpty() && valor != null) {

				switch (atributo) {
				case "nombre":
				case "lugar":
				case "edicion":
				case "estado":
					sql += " AND " + atributo + " LIKE ?";
					filtrar = true;
					break;

				case "id_organizador":
				case "id_evento":
				case "tipo":
					sql += " AND " + atributo + " = ?";
					filtrar = true;
					break;
				}
			}

			sql += " ORDER BY fecha_inicio";

			PreparedStatement ps = bd.con.prepareStatement(sql);

			if (filtrar) {

				if ("nombre".equals(atributo) || "lugar".equals(atributo) || "edicion".equals(atributo)
						|| "estado".equals(atributo)) {

					ps.setString(1, "%" + valor.toString() + "%");

				} else if (valor instanceof Integer) {

					ps.setInt(1, (Integer) valor);

				} else {

					ps.setObject(1, valor);
				}
			}

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				eventos.add(crearEvento(rs));
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

		String sql = "SELECT e.* FROM eventos e " + "JOIN inscripciones i ON e.id_evento = i.id_evento "
				+ "WHERE i.id_voluntario = ?";

		List<Evento> eventos = new ArrayList<>();

		try {
			AccesoBD bd = new AccesoBD();
			PreparedStatement ps = bd.con.prepareStatement(sql);
			ps.setInt(1, idUsuario);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				eventos.add(AccesoBD.crearEvento(rs));
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

		try {
			AccesoBD bd = new AccesoBD();
			PreparedStatement ps = bd.con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}

			rs.close();
			ps.close();
			bd.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public boolean inscribir(int idUser, int idEvento) {
		String sql = "INSERT INTO inscripciones (id_voluntario, id_evento, estado) VALUES (?, ?, 'Pendiente') "
				+ "ON DUPLICATE KEY UPDATE estado = 'Pendiente'";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, idUser);
			ps.setInt(2, idEvento);

			ps.executeUpdate();
			ps.close();
			return true;
		} catch (Exception m) {
			m.printStackTrace();
			return false;
		}
	}

	public boolean usuarioInscrito(int idVoluntario, int idEvento) {
		String sql = "SELECT * FROM inscripciones WHERE id_voluntario = ? AND id_evento = ? AND estado != 'Cancelado'";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, idVoluntario);
			ps.setInt(2, idEvento);

			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		} catch (Exception m) {
			m.printStackTrace();
			return false;
		}
	}

	public ArrayList<Integer> obtenerInscripciones(int idVoluntario) throws SQLException {
		String sql = "select * from inscripciones where id_voluntario=?";
		ArrayList<Integer> idsEventos = new ArrayList<>();

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, idVoluntario);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			idsEventos.add(rs.getInt("id_evento"));
		}

		rs.close();
		ps.close();
		return idsEventos;
	}

	public void borrarVoluntario(int idUsuario) throws SQLException {
		String sql = "UPDATE voluntarios SET nombre=?, apellidos=?, email=?, pass=?, fechaNac=NULL, telefono=?, vehiculo=?, discapacidad=? WHERE id_voluntario=?";

		PreparedStatement ps = con.prepareStatement(sql);

		ps.setString(1, "Usuario eliminado");
		ps.setString(2, "");
		ps.setString(3, "eliminado_" + idUsuario + "@eliminado.com");
		ps.setString(4, "");
		ps.setString(5, "");
		ps.setBoolean(6, false);
		ps.setInt(7, 0);
		ps.setInt(8, idUsuario);

		ps.executeUpdate();
		ps.close();
	}

	public void borrarOrganizador(int idOrganizador) throws SQLException {
		String sql = "UPDATE organizadores SET nombre=?, apellidos=?, email=?, pass=?, telefono=?, empresa=? WHERE id_organizador=?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, "Usuario eliminado");
			ps.setString(2, "");
			ps.setString(3, "eliminado_" + idOrganizador + "@eliminado.com");
			ps.setString(4, "");
			ps.setString(5, "");
			ps.setString(6, "");
			ps.setInt(7, idOrganizador);
			ps.executeUpdate();
		}
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

		String sql = "SELECT i.*, v.*, " + "(SELECT ROUND(AVG(i2.valoracion), 2) " + " FROM inscripciones i2 "
				+ " WHERE i2.id_voluntario = v.id_voluntario) AS media " + "FROM inscripciones i "
				+ "JOIN voluntario_media v ON i.id_voluntario = v.id_voluntario " + "WHERE i.id_evento = ?";

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
				Voluntario v = crearVoluntario(rs);

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
		String sql = "UPDATE inscripciones SET estado = ? WHERE id_voluntario = ? AND id_evento = ?";

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
		String sql = "UPDATE inscripciones SET estado = ? WHERE id_voluntario = ? AND id_evento = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "Cancelado");
			ps.setInt(2, idUser);
			ps.setInt(3, idEvento);

			ps.executeUpdate();
			ps.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static int contadorHome(String tabla) {
		int numContador = 0;
		String sql = "SELECT COUNT(*) FROM " + tabla;

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

	public static int contadorInscripciones() {
		int numContador = 0;
		String sql = "SELECT COUNT(*) FROM inscripciones WHERE estado != 'Cancelado'";

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

	// Método para listar voluntarios y empresas según variable
	public ArrayList<Object> PanelAdmin(String tabla) {

		ArrayList<Object> lista = new ArrayList<>();

		String sql = "";

		try {
			PreparedStatement ps = null;
			ResultSet rs = null;

			switch (tabla) {

			// =========================
			// VOLUNTARIOS
			// =========================
			case "voluntarios":

				sql = "SELECT * FROM voluntario_media WHERE nombre != 'Usuario eliminado'";
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();

				while (rs.next()) {
					Voluntario v = crearVoluntario(rs);
					lista.add(v);
				}
				break;

			// =========================
			// EMPRESAS
			// =========================
			case "empresas":

				sql = "SELECT empresa, COUNT(id_organizador) AS organizadores "
						+ "FROM organizadores WHERE nombre != 'Usuario eliminado' GROUP BY empresa";
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();

				while (rs.next()) {
					Map<String, Object> fila = new HashMap<>();
					fila.put("empresa", rs.getString("empresa"));
					fila.put("total", rs.getInt("organizadores"));
					fila.put("id", rs.getString("empresa"));
					lista.add(fila);
				}
				break;

			}

			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public boolean valorarVoluntario(int idInscripcion, int nota) {
		// Validar que la nota sea de 1 a 5
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

	public boolean crearTarea(String nombre) {
		String sql = "INSERT INTO tareas (nombre) VALUES (?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, nombre);
			ps.executeUpdate();
			ps.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int insertarPuntoControl(String nombre, String descripcion, int idEvento) {
		String sql = "INSERT INTO puntos_control (nombre, descripcion, id_evento) VALUES (?, ?, ?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, nombre);
			ps.setString(2, descripcion);
			ps.setInt(3, idEvento);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			int id = rs.next() ? rs.getInt(1) : -1;
			rs.close();
			ps.close();
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public boolean asignarTareaAPuntoControl(int idPc, int idTarea) {
		String sql = "INSERT INTO asignacion_tareas (id_pc, id_tarea) VALUES (?, ?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, idPc);
			ps.setInt(2, idTarea);
			ps.executeUpdate();
			ps.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static List<Tarea> obtenerTareas() {
		List<Tarea> tareas = new ArrayList<>();
		String sql = "SELECT id_tareas, nombre FROM tareas";
		try {
			AccesoBD bd = new AccesoBD();
			PreparedStatement ps = bd.con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				tareas.add(new Tarea(rs.getInt("id_tareas"), rs.getString("nombre")));
			}
			rs.close();
			ps.close();
			bd.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tareas;
	}

	public Voluntario obtenerVoluntarioPorId(int idUsuario) throws SQLException {

		String sql = "SELECT * FROM voluntario_media WHERE id_voluntario = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, idUsuario);

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					return crearVoluntario(rs);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

		return null;
	}

	public boolean actualizarEstadoEvento(Evento e) {
		String sql = "UPDATE eventos SET estado = ? WHERE id_evento = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, e.getEstado());
			ps.setInt(2, e.getIdEvento());

			int filasAfectadas = ps.executeUpdate();
			ps.close();

			return filasAfectadas > 0;

		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public void actualizarNombreEvento(int idEvento, String nombre) {

		String sql = "UPDATE eventos SET nombre = ? WHERE id_evento = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, nombre);
			ps.setInt(2, idEvento);

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Organizador> obtenerOrganizadoresPorEmpresa(String empresa) throws SQLException {
		String sql = "SELECT * FROM organizadores WHERE empresa = ? AND nombre != 'Usuario eliminado'";
		List<Organizador> lista = new ArrayList<>();

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, empresa);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					lista.add(crearOrganizador(rs));
				}
			}
		}
		return lista;
	}

	public void borrarOrganizadoresPorEmpresa(String empresa) throws SQLException {
		String sqlIds = "SELECT id_organizador FROM organizadores WHERE empresa = ?";
		List<Integer> ids = new ArrayList<>();

		try (PreparedStatement ps = con.prepareStatement(sqlIds)) {
			ps.setString(1, empresa);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					ids.add(rs.getInt("id_organizador"));
				}
			}
		}

		String sql = "UPDATE organizadores SET nombre=?, apellidos=?, email=?, pass=?, telefono=?, empresa=? WHERE id_organizador=?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			for (int id : ids) {
				ps.setString(1, "Usuario eliminado");
				ps.setString(2, "");
				ps.setString(3, "eliminado_" + id + "@eliminado.com");
				ps.setString(4, "");
				ps.setString(5, "");
				ps.setString(6, "");
				ps.setInt(7, id);
				ps.executeUpdate();
			}
		}
	}

	public static String obtenerEstadoInscripcion(int idVoluntario, int idEvento) {
		String sql = "SELECT estado FROM inscripciones WHERE id_voluntario = ? AND id_evento = ? AND estado NOT IN ('Cancelado', 'Rechazado')";
		try {
			AccesoBD bd = new AccesoBD();
			PreparedStatement ps = bd.con.prepareStatement(sql);
			ps.setInt(1, idVoluntario);
			ps.setInt(2, idEvento);
			ResultSet rs = ps.executeQuery();
			String estado = rs.next() ? rs.getString("estado") : null;
			rs.close();
			ps.close();
			bd.disconnect();
			return estado;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int contarInscritos(int idEvento) {
		String sql = "SELECT COUNT(*) FROM inscripciones WHERE id_evento = ? AND estado = 'Aceptado'";
		try {
			AccesoBD bd = new AccesoBD();
			PreparedStatement ps = bd.con.prepareStatement(sql);
			ps.setInt(1, idEvento);
			ResultSet rs = ps.executeQuery();
			int count = rs.next() ? rs.getInt(1) : 0;
			rs.close();
			ps.close();
			bd.disconnect();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public boolean eliminarEvento(int idEvento) throws SQLException {
		// 1. Asignaciones ligadas a puntos de control o inscripciones de este evento
		String sql1 = "DELETE FROM asignacion_tareas WHERE id_pc IN "
				+ "(SELECT id_pc FROM puntos_control WHERE id_evento = ?) " + "OR id_inscripcion IN "
				+ "(SELECT id_inscripcion FROM inscripciones WHERE id_evento = ?)";
		PreparedStatement ps1 = con.prepareStatement(sql1);
		ps1.setInt(1, idEvento);
		ps1.setInt(2, idEvento);
		ps1.executeUpdate();
		ps1.close();

		// 2. Inscripciones
		String sql2 = "DELETE FROM inscripciones WHERE id_evento = ?";
		PreparedStatement ps2 = con.prepareStatement(sql2);
		ps2.setInt(1, idEvento);
		ps2.executeUpdate();
		ps2.close();

		// 3. Puntos de control
		String sql3 = "DELETE FROM puntos_control WHERE id_evento = ?";
		PreparedStatement ps3 = con.prepareStatement(sql3);
		ps3.setInt(1, idEvento);
		ps3.executeUpdate();
		ps3.close();

		// 4. El evento en sí
		String sql4 = "DELETE FROM eventos WHERE id_evento = ?";
		PreparedStatement ps4 = con.prepareStatement(sql4);
		ps4.setInt(1, idEvento);
		int rows = ps4.executeUpdate();
		ps4.close();

		return rows > 0;
	}

	public static List<String> obtenerPuntosControl(String idEvento) {

		List<String> puntosControl = new ArrayList<>();
		String sql = "SELECT nombre FROM puntos_control WHERE id_evento = ?";

		try {
			AccesoBD bd = new AccesoBD();

			PreparedStatement ps = bd.con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(idEvento));

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				puntosControl.add(rs.getString("nombre"));
			}

			rs.close();
			ps.close();
			bd.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return puntosControl;
	}

	public void asignarPuntoControl(int idInscripcion, String nombrePuntoControl) {
		// Consulta para actualizar solo la primera fila encontrada que no tenga
		// inscripción asignada
		String sql = "UPDATE asignacion_tareas " + "SET id_inscripcion = ? " + "WHERE id_inscripcion IS NULL "
				+ "AND id_pc = (SELECT id_pc FROM puntos_control WHERE nombre = ? LIMIT 1) " + "LIMIT 1";

		try (PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, idInscripcion);
			ps.setString(2, nombrePuntoControl);

			int filasAfectadas = ps.executeUpdate();

			if (filasAfectadas > 0) {
				System.out.println("Asignación realizada con éxito.");
			} else {
				System.out.println("No se encontraron huecos libres en este punto de control.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean estaAsignado(int idInscripcion) {
		String sql = "SELECT COUNT(*) FROM asignacion_tareas WHERE id_inscripcion = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idInscripcion);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean emailExiste(String email) throws SQLException {
		String emailLimpio = email.toLowerCase().trim();

		String sqlVol = "SELECT 1 FROM voluntarios WHERE LOWER(TRIM(email)) = ?";
		try (PreparedStatement ps = con.prepareStatement(sqlVol)) {
			ps.setString(1, emailLimpio);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return true;
			}
		}

		String sqlOrg = "SELECT 1 FROM organizadores WHERE LOWER(TRIM(email)) = ?";
		try (PreparedStatement ps = con.prepareStatement(sqlOrg)) {
			ps.setString(1, emailLimpio);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return true;
			}
		}

		return false;
	}

	public String obtenerEstadoInscripcion2(int idVoluntario, int idEvento) throws SQLException {
		String sql = "SELECT estado FROM inscripciones WHERE id_voluntario = ? AND id_evento = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idVoluntario);
			ps.setInt(2, idEvento);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return rs.getString("estado");
			}
		}
		return null;
	}

}