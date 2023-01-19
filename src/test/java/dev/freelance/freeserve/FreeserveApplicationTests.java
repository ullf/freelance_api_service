package dev.freelance.freeserve;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import dev.freelance.freeserve.config.JwtTokenFilter;
import dev.freelance.freeserve.config.SecurityConfiguration;
import dev.freelance.freeserve.controller.AuthController;
import dev.freelance.freeserve.controller.ClientController;
import dev.freelance.freeserve.entity.AbstractClient;
import dev.freelance.freeserve.entity.AbstractOrder;
import dev.freelance.freeserve.entity.AuthRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.freelance.freeserve.repository.ClientRepository;
import dev.freelance.freeserve.repository.OrderRepository;
import dev.freelance.freeserve.service.AbstractClientService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ContextConfiguration(classes= SecurityConfiguration.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class FreeserveApplicationTests {

	@Autowired
	private MockMvc mvc;
	AbstractClient client;

	@Test
	void testCreateClient() throws Exception {
		client = new AbstractClient();
		client.setNickname("nick");
		client.setIndicator(true);
		client.setPassword("pass000");
		client.setName("John");

		ObjectMapper objectMapper = new ObjectMapper();
		String authRequestJson = objectMapper.writeValueAsString(client);
		mvc.perform(MockMvcRequestBuilders.post("/createClient").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(authRequestJson)).
				andExpect(status().isOk());

	}

	@Test
	void testLogin() throws Exception {
		AuthRequest authRequest = new AuthRequest();
		authRequest.setNickname("frl_nick");
		authRequest.setPassword("pass000");
		ObjectMapper objectMapper = new ObjectMapper();
		String authRequestJson = objectMapper.writeValueAsString(authRequest);
		mvc.perform(MockMvcRequestBuilders.post("/login").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(authRequestJson)).
				andExpect(status().isOk()).andExpect(header().string(HttpHeaders.AUTHORIZATION, notNullValue())).
				andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("frl_nick"));
		//var obj = (AbstractClient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//System.out.println("order:"+obj.getId());
	}

	@Test
	void testFindClientByUsername() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/findClientByNickname/{nickname}","frl_nick")).andExpect(status().isOk());
	}

	/*@Test
	void testCreateOrder() throws Exception {
		AbstractOrder order = new AbstractOrder();
		order.setAbstractName("order 1");
		order.setAbstractDescription("description of order 1");
		order.setClientsId(client);
		if ( client != null ) {
			System.out.println(client.getPassword());
			ObjectMapper objectMapper = new ObjectMapper();
			String authRequestJson = objectMapper.writeValueAsString(order);
			mvc.perform(MockMvcRequestBuilders.post("/createOrder").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(authRequestJson)).
					andExpect(status().isOk());
		} else {
			System.out.println(client.getNickname());
		}
	}*/

	/*@Test
	void testTakeOrder() throws Exception {
		AbstractOrder order = new AbstractOrder();
		order.setAbstractName("order 1");
		order.setAbstractDescription("description of order 1");
		//order.setClientsId(client);
		ClientRepository clientRepository = Mockito.mock(ClientRepository.class);
		when(clientRepository.findAbstractClientByNickname("frl_nick")).thenReturn(client);

		OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
		when(orderRepository.save(order)).thenReturn(order);
		Authentication authentication = Mockito.mock(Authentication.class);
// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		//var obj = (AbstractClient) authentication.getPrincipal();
		//System.out.println(obj.getPassword());
		mvc.perform(MockMvcRequestBuilders.post("/takeOrder/{orderId}",6)).andExpect(status().isOk());
	}*/



	/*@Test
	public void Message() throws JsonProcessingException, Exception {
		mvc.perform(MockMvcRequestBuilders.post("/login").content(mapper.writeValueAsString(new AuthRequest("mark","09721"))).accept(org.springframework.http.MediaType.APPLICATION_JSON).contentType(org.springframework.http.MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		System.out.println("ok!");
	}/*

	 */
}
