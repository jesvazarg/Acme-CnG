
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Actor;
import domain.Comment;
import domain.Offer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CommentServiceTest extends AbstractTest {

	//System under test-------------------------------------		

	@Autowired
	private CommentService	commentService;

	@Autowired
	private OfferService	offerService;

	@Autowired
	private ActorService	actorService;


	//Tests---------------------------------------------------

	//NOTA IMPORTANTE
	//SE HA DECIDIDO NO USAR EL ESQUEMA VISTO EN TEORIA YA QUE PERDERIAMOS DEMASIADO TIEMPO
	//EN SU IMPLEMENTACION. 

	//Mostrar comentarios

	//Mostrar un comentario
	@Test
	public void testFindOne() {

		Comment result;

		result = this.commentService.findOne(91);

		System.out.println("Test findOne: " + result.getId() + ", title: " + result.getTitle());

		System.out.println("----------------------------------------");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindOneNull() {
		Comment result;

		result = this.commentService.findOne(0);
	}

	//Mostrar todos lo comentarios
	@Test
	public void testFindAll() {

		Collection<Comment> result = new ArrayList<Comment>();

		result = this.commentService.findAll();

		System.out.println("Test findAll: Encontrados un total de " + result.size() + " resultados");

		System.out.println("----------------------------------------");
	}

	// Mostrar solo los comentarios no baneados
	@Test
	public void testgetCommentsFilterBan() {

		final Actor actor = this.actorService.findOne(55);

		this.authenticate(actor.getUserAccount().getUsername());

		final Collection<Comment> todos = this.commentService.findAll();

		final Collection<Comment> noBaneados = this.commentService.getCommentsFilterBan(todos);

		final int baneados = todos.size() - noBaneados.size();

		System.out.println("Se han ocultado " + baneados + " comentarios baneados.");
		System.out.println("----------------------------------------");

		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testgetCommentsFilterBanNotCorrectUser() {

		final Actor actor = this.actorService.findOne(55);

		//		this.authenticate(actor.getUserAccount().getUsername());

		final Collection<Comment> todos = this.commentService.findAll();

		final Collection<Comment> noBaneados = this.commentService.getCommentsFilterBan(todos);

		final int baneados = todos.size() - noBaneados.size();

		System.out.println("Se han ocultado " + baneados + " comentarios baneados.");
		System.out.println("----------------------------------------");

		//		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testgetCommentsFilterBanNull() {

		final Actor actor = this.actorService.findOne(55);

		this.authenticate(actor.getUserAccount().getUsername());

		final Collection<Comment> noBaneados = this.commentService.getCommentsFilterBan(null);

		System.out.println("----------------------------------------");

		this.unauthenticate();
	}

	//Crear y guardar un nuevo comentario para una oferta
	@Test
	public void testCreateAndSave() {

		super.authenticate("customer1");
		final int comentariosActuales = this.commentService.findAll().size();

		final Offer ofertaAComentar = this.offerService.findOne(60);

		final Comment comentario = this.commentService.create(ofertaAComentar);
		comentario.setTitle("Comentario de prueba");
		comentario.setText("Texto de prueba");
		comentario.setStarsNumber(5);

		this.commentService.save(comentario);

		final int comentariosNuevos = this.commentService.findAll().size();

		if (comentariosNuevos - comentariosActuales == 1)
			System.out.println("Nuevo comentario guardado correctamente");

		System.out.println("----------------------------------------");

		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNotAuthenticated() {

		final int comentariosActuales = this.commentService.findAll().size();

		final Offer ofertaAComentar = this.offerService.findOne(60);

		final Comment comentario = this.commentService.create(ofertaAComentar);
		comentario.setTitle("Comentario de prueba");
		comentario.setText("Texto de prueba");
		comentario.setStarsNumber(5);

		this.commentService.save(comentario);

		final int comentariosNuevos = this.commentService.findAll().size();

		if (comentariosNuevos - comentariosActuales == 1)
			System.out.println("Nuevo comentario guardado correctamente");

		System.out.println("----------------------------------------");

	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNullCreate() {

		super.authenticate("customer1");
		final int comentariosActuales = this.commentService.findAll().size();

		final Offer ofertaAComentar = this.offerService.findOne(60);

		final Comment comentario = this.commentService.create(null);
		//		comentario.setTitle("Comentario de prueba");
		//		comentario.setText("Texto de prueba");
		//		comentario.setStarsNumber(5);
		//		
		//		this.commentService.save(comentario);
		//		
		//		int comentariosNuevos = this.commentService.findAll().size();
		//		
		//		if(comentariosNuevos-comentariosActuales==1){
		//		System.out.println("Nuevo comentario guardado correctamente");
		//		}

		System.out.println("----------------------------------------");

		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNullSave() {

		super.authenticate("customer1");
		final int comentariosActuales = this.commentService.findAll().size();

		final Offer ofertaAComentar = this.offerService.findOne(60);

		final Comment comentario = this.commentService.create(ofertaAComentar);
		comentario.setTitle("Comentario de prueba");
		comentario.setText("Texto de prueba");
		comentario.setStarsNumber(5);

		this.commentService.save(null);

		final int comentariosNuevos = this.commentService.findAll().size();

		if (comentariosNuevos - comentariosActuales == 1)
			System.out.println("Nuevo comentario guardado correctamente");

		System.out.println("----------------------------------------");

		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNotAuthenticated2() {

		super.authenticate("customer1");
		final int comentariosActuales = this.commentService.findAll().size();

		final Offer ofertaAComentar = this.offerService.findOne(60);

		final Comment comentario = this.commentService.create(ofertaAComentar);
		comentario.setTitle("Comentario de prueba");
		comentario.setText("Texto de prueba");
		comentario.setStarsNumber(5);
		this.unauthenticate();

		this.commentService.save(comentario);

		final int comentariosNuevos = this.commentService.findAll().size();

		if (comentariosNuevos - comentariosActuales == 1)
			System.out.println("Nuevo comentario guardado correctamente");

		System.out.println("----------------------------------------");

	}

	//Banear un comentario siendo administrador

	@Test
	public void testBanComments() {

		this.authenticate("admin");

		final Comment comentario = this.commentService.findOne(90);

		this.commentService.banComment(comentario);

		System.out.println("El comentario " + comentario.getId() + " ha sido baneado.");
		System.out.println("----------------------------------------");
		this.unauthenticate();

	}

	@Test(expected = IllegalArgumentException.class)
	public void testBanNullComments() {

		this.authenticate("admin");

		final Comment comentario = this.commentService.findOne(90);

		this.commentService.banComment(null);

		System.out.println("El comentario " + comentario.getId() + " ha sido baneado.");
		System.out.println("----------------------------------------");

	}

	@Test(expected = IllegalArgumentException.class)
	public void testBanCommentsNotAuthenticated() {

		final Comment comentario = this.commentService.findOne(90);

		this.commentService.banComment(null);

		System.out.println("El comentario " + comentario.getId() + " ha sido baneado.");
		System.out.println("----------------------------------------");

	}
	//Eliminar un comentario solamente siendo su autor

	@Test
	public void testDelete() {

		this.authenticate("customer1");
		final Comment comentario = this.commentService.findOne(92);

		this.commentService.delete(comentario);

		System.out.println("El comentario " + comentario.getId() + " ha sido eliminado.");
		System.out.println("----------------------------------------");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteNullComment() {

		this.authenticate("customer1");

		this.commentService.delete(null);

		System.out.println("----------------------------------------");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteNotAuthenticated() {

		final Comment comentario = this.commentService.findOne(92);

		this.commentService.delete(comentario);

		System.out.println("----------------------------------------");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteDistinctUser() {

		this.authenticate("customer2");
		final Comment comentario = this.commentService.findOne(92);

		this.commentService.delete(comentario);

		this.unauthenticate();
		System.out.println("----------------------------------------");
	}
}
