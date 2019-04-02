package es.avalon.curso.repositorios;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import es.avalon.curso.negocio.Libro;
import es.avalon.utilidades.persistencia.DBHelper;

public class LibroRepository {

	
	public List<Libro> buscarTodos() {

		List<Libro> libros = new ArrayList<Libro>();
		String sql = "select * from Libro";
		
		System.out.println("ENTRA");
		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);
				ResultSet rs = sentencia.executeQuery(sql)) {
			System.out.println("SALE");
			while (rs.next()) {

				String titulo = rs.getString("titulo");
				String autor = rs.getString("autor");
				int paginas = rs.getInt("paginas");
				Libro li = new Libro(titulo, autor, paginas);

				libros.add(li);
				
			}

		} catch (Exception e) {
			System.out.println("Error: " + e);
			// e.printStackTrace();
			
			throw new RuntimeException("ha ocurrido un error al obtener el listado de libros ", e);
			
		}

		return libros;
	}

	public void insertar(Libro libro){
		System.out.println(libro.getAutor() + " " + libro.getTitulo() + " " + libro.getPaginas());
		String sql = " insert into libro (titulo, autor, paginas) values(?,?,?)";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {

			sentencia.setString(1, libro.getTitulo());
			sentencia.setString(2, libro.getAutor());
			sentencia.setDouble(3, libro.getPaginas());
			sentencia.executeUpdate();

			System.out.println(libro.getTitulo() + " fue insertado");

		} catch (Exception e) {
		//System.err.println("ha ocurrido un error al insertar el libro"+e.getMessage());
			throw new RuntimeException("ha ocurrido un error al insertar el "+libro.getTitulo(), e);	}
	}

	public void deleteLibro(Libro libro) {

		String sql = "delete from libro where titulo = ?";
		// String sql = "delete from ordenador where modelo = '"+this.getModelo()+"'";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {

			sentencia.setString(1, libro.getTitulo());
			sentencia.executeUpdate();
			System.out.println(libro.getTitulo() + " fue eliminado");

		} catch (Exception e) {
			System.err.println(e.getMessage());
			
			
			throw new RuntimeException("ha ocurrido un error al eliminar el "+libro.getTitulo()+" ", e);
			
		}

	}

	public Libro buscarUnLibroPorTitulo(String titulo) {

		String sql = "select * from Libro where titulo = ?";
		Libro libro = null;

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {

			sentencia.setString(1, titulo);
			ResultSet rs = sentencia.executeQuery();// No le pasamos la sql
			rs.next();

			libro = new Libro(rs.getString("titulo"), rs.getString("autor"), rs.getInt("paginas"));
			System.out.println("************* " + titulo + " fue encontrado");

		} catch (Exception e) {
			System.out.println("Error en clase: " + e);
			
			throw new RuntimeException("ha ocurrido un error al editar el "+titulo+": ", e);
			
		}
		return libro;
	}

	public void updateLibro(Libro libro) {

		String sql = "update libro set autor = ?, paginas = ? where titulo = ?";
		// String sql = "update ordenador set marca ='"+this.getMarca()+"',precio
		// ="+this.getPrecio()+" where modelo = '"+this.getModelo()+"'";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {

			sentencia.setString(1, libro.getAutor());
			sentencia.setDouble(2, libro.getPaginas());
			sentencia.setString(3, libro.getTitulo());
			sentencia.executeUpdate();

			System.out.println(libro.getTitulo() + " se actualizo correctamente");

		} catch (Exception e) {

			//System.err.println(e.getMessage());
			
			
			throw new RuntimeException("ha ocurrido un error al actualizar "+libro.getTitulo()+" ", e);
			
		}
	}

	public List<Libro> filtrarPorCampo(String filtro) {

		System.out.println("filtrarPorCampo le llega: " + filtro);
		List<Libro> lista = new ArrayList<Libro>();
		String sql = "Select * from libro order by " + filtro;

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);
				ResultSet rs = sentencia.executeQuery()) {

			while (rs.next()) {

				Libro lib = new Libro(rs.getString("titulo"), rs.getString("autor"),
						Integer.parseInt(rs.getString("paginas")));
				lista.add(lib);
			}

			System.out.println(" filtrado por " + filtro);

		} catch (Exception e) {
			System.out.println("Error al filtrarPorCampo: " + e);
			
			throw new RuntimeException("ha ocurrido un error al filtrar por "+filtro+" ", e);
			
		}
		return lista;

	}

	public  List<Libro> buscarPorTitulo(String titulo) {

		System.out.println("buscarPorTitulo le llega: " + titulo);
		List<Libro> lista = new ArrayList<Libro>();
		String sql = "Select * from libro where titulo= ? ";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {

			sentencia.setString(1, titulo);
			ResultSet rs = sentencia.executeQuery();

			while (rs.next()) {

				Libro lib = new Libro(rs.getString("titulo"), rs.getString("autor"),
						Integer.parseInt(rs.getString("paginas")));
				lista.add(lib);
				System.out.println(" Encontrado " + titulo);
			}


		} catch (Exception e) {
			System.out.println("Error al buscarPorTitulo: " + e);
			throw new RuntimeException("ha ocurrido un error al buscar un libro por titulo ", e);
		}
		return lista;

	}
}
