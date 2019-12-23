package com.graph.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.graph.mail.service.impl.ClientCredentialGrant;
import com.graph.mail.service.impl.GraphApiMailImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GraphApplication.class)
public class GraphApiMailImplTest {
	@Autowired
	private ClientCredentialGrant clientCredentialGrant;
	@Autowired
	private GraphApiMailImpl graphApiMail;

	@Test
	public void GraphApiMailServiceTest() throws Exception {

		graphApiMail.getMail();
		
	}

}
