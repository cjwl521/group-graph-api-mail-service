package com.graph.mail.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import org.springframework.stereotype.Service;

import com.graph.mail.common.Singleton;
import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ClientCredentialParameters;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.SilentParameters;

@Service
public class ClientCredentialGrant {

	private static final String CLIENT_ID = "064a66fa-ef83-4776-9997-7c094845667d";
	private static final String TENANT_GUID = "8dfcce69-73e6-4b79-9e19-6ad647dd1929";
	private static final String CLIENT_SECRET = "?WTJ_G4=kU[2HvYiMS4GSI7?ZL?rB-F.";
	private static final String SCOPES = "https://microsoftgraph.chinacloudapi.cn/.default";
	
	private static String token = "";

	public void getAccessTokenByClientCredentialGrant() throws Exception {

		String url = "https://login.chinacloudapi.cn/" + TENANT_GUID + "/oauth2/v2.0/token";
		ConfidentialClientApplication app = ConfidentialClientApplication
				.builder(CLIENT_ID, ClientCredentialFactory.createFromSecret(CLIENT_SECRET)).authority(url).build();

		ClientCredentialParameters clientCredentialParam = ClientCredentialParameters
				.builder(Collections.singleton(SCOPES)).build();

		CompletableFuture<IAuthenticationResult> future = app.acquireToken(clientCredentialParam);

		BiConsumer<IAuthenticationResult, Throwable> processAuthResult = (res, ex) -> {
			if (ex != null) {
				System.out.println("Oops! We have an exception - " + ex.getMessage());
			}
			System.out.println("Access Token - " + res.accessToken());
			token = res.accessToken();
		};

		future.whenCompleteAsync(processAuthResult);
		future.join();

		SilentParameters silentParameters = SilentParameters.builder(Collections.singleton(SCOPES)).build();

		future = app.acquireTokenSilently(silentParameters);

		future.whenCompleteAsync(processAuthResult);
		future.join();
		Map<String, String> map = new HashMap<String, String>();
		map.put("TOKEN", token);
		Singleton.getInstance().setMap(map);
	}

}