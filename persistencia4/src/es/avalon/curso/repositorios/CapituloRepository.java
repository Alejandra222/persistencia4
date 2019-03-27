package es.avalon.curso.repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import es.avalon.curso.negocio.Capitulo;
import es.avalon.utilidades.persistencia.DBHelper;

public class CapituloRepository {

	
	public List<Capitulo> buscarTodosLosCapitulos() {

		List<Capitulo> capitulos = new ArrayList<Capitulo>();
		String sql = "select * from capitulo";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);
				ResultSet rs = sentencia.executeQuery(sql)) {

			while (rs.next()) {

				String titulo = rs.getString("titulo");
				int paginas = rs.getInt("paginas");
				String libro_titulo = rs.getString("libro_titulo");
				Capitulo cap = new Capitulo(titulo, paginas, libro_titulo);

				capitulos.add(cap);
			}

			System.out.println("Capitulos encontrados: " + capitulos.size());

		} catch (Exception e) {
			System.out.println("Error buscarTodosLosCapitulos: " + e);
			// e.printStackTrace();
		}

		return capitulos;
	}

	public static List<Capitulo> buscarCapituloPorLibro(String tituloLibro) {

		System.out.println("buscarCapituloPorLibro le llega: " + tituloLibro);
		List<Capitulo> lista = new ArrayList<Capitulo>();
		String sql = "Select * from capitulo where Libro_Titulo= ? ";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {

			sentencia.setString(1, tituloLibro);
			ResultSet rs = sentencia.executeQuery();

			while (rs.next()) {

				Capitulo lib = new Capitulo(rs.getString("titulo"), Integer.parseInt(rs.getString("paginas")));
				lista.add(lib);
			}

			System.out.println(" Encontrado " + tituloLibro);

		} catch (Exception e) {
			System.out.println("Error al buscarPorTitulo: " + e);
		}
		return lista;

	}

	public void insertar(Capitulo capitulo) {
		System.out.println(capitulo.getLibro_titulo() + " " + capitulo.getTitulo() + " " + capitulo.getPaginas());
		String sql = " insert into capitulo (titulo, paginas, libro_titulo) values(?,?,?)";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {

			sentencia.setString(1, capitulo.getTitulo());
			sentencia.setInt(2, capitulo.getPaginas());
			sentencia.setString(3, capitulo.getLibro_titulo());
			sentencia.executeUpdate();

			System.out.println(capitulo.getTitulo() + " fue insertado Capitulo");

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	public void deleteCapitulo(Capitulo capitulo) {

		String sql = "delete from capitulo where titulo = ? and libro_titulo = ?";
		// String sql = "delete from ordenador where modelo = '"+this.getModelo()+"'";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {

			sentencia.setString(1, capitulo.getTitulo());
			sentencia.setString(2, capitulo.getLibro_titulo());
			sentencia.executeUpdate();
			System.out.println(capitulo.getTitulo() + " fue eliminado");

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	public static Capitulo buscarCapituloPorTitulo(String titulo) {

		System.out.println("buscarCapituloPorTitulo le llega: " + titulo);

		String sql = "Select * from capitulo where titulo= ? ";

		Capitulo lib = null;
		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {

			sentencia.setString(1, titulo);
			ResultSet rs = sentencia.executeQuery();
			rs.next();

			lib = new Capitulo(rs.getString("titulo"), Integer.parseInt(rs.getString("paginas")));

			System.out.println(" Encontrado " + lib.getTitulo());

		} catch (Exception e) {
			System.out.println("Error al buscarPorTitulo: " + e);
		}
		return lib;

	}

	public void updateCapitulo(Capitulo capitulo) {

		String sql = "update capitulo set paginas = ? where titulo = ? and libro_titulo = ?";
		// String sql = "update ordenador set marca ='"+this.getMarca()+"',precio
		// ="+this.getPrecio()+" where modelo = '"+this.getModelo()+"'";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {

			sentencia.setInt(1, capitulo.getPaginas());
			sentencia.setString(2, capitulo.getTitulo());
			sentencia.setString(3, capitulo.getLibro_titulo());
			sentencia.executeUpdate();

			System.out.println(capitulo.getTitulo() + " se actualizo correctamente");

		} catch (Exception e) {

			System.err.println(e.getMessage());
		}
	}

}
