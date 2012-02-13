package org.wso2.wsas.client;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.wso2.wsas.service.SandeshaServiceStub;
import org.apache.sandesha2.client.SandeshaClientConstants;

public class RequestReplyAnnonInorderClient{

public static void main(String args[])throws AxisFault{
	
	ConfigurationContext cc = ConfigurationContextFactory.createConfigurationContextFromFileSystem(args[0],args[0]+"\\conf\\axis2.xml");

	SandeshaServiceStub stub = new SandeshaServiceStub(cc);
	SandeshaServiceStub.EchoInt request = new SandeshaServiceStub.EchoInt();

	stub._getServiceClient().engageModule("sandesha2");

	for (int i = 0; i < 9; i++) {

		request.setX(i);

		try {
			SandeshaServiceStub.EchoIntResponse response = stub.echoInt(request);
			System.out.println(response.get_return());
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

//	Setting the last message
	stub._getServiceClient().getOptions().setProperty(SandeshaClientConstants.LAST_MESSAGE, Constants.VALUE_TRUE);

	try {
		SandeshaServiceStub.EchoIntResponse response = stub.echoInt(request);
		System.out.println(response.get_return());
	} catch (RemoteException e) {
		e.printStackTrace();
	}

	try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
}
}