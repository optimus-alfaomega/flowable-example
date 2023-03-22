package org.flowable.simpleTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;

public class FlowableFLowSimpleTask {

	public static void main(String[] args) {
		// TODO Auto-generated method stub


		 ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
			      .setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1")
			      .setJdbcUsername("sa")
			      .setJdbcPassword("")
			      .setJdbcDriver("org.h2.Driver")
			      .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		
		 ProcessEngine processEngine = cfg.buildProcessEngine();		
		
/*   creación de repository Service*/
		 
		 RepositoryService repositoryService = processEngine.getRepositoryService();
		 
/*   fin de creación de repository Service*/
		 
		 
		 Deployment deployment = repositoryService.createDeployment()
		  .addClasspathResource("Ejemplo1.bpmn20.xml")
		  .deploy();
		 
		 
		   
	     	 ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				  .deploymentId(deployment.getId())
				  .singleResult();
				System.out.println("Found process definition : " + processDefinition.getName());
				
				
				
				
				
/* solicitud de variables como parametros de entrada del proceso */
				
				
				Scanner scanner= new Scanner(System.in);
		


				System.out.println("Registre el documento");
 				String document = scanner.nextLine();

				System.out.println("Registre el tipo de poliza");
				String tipoPoliza = scanner.nextLine();

				System.out.println("Registre nombre de intermediario");
				String intermediario = scanner.nextLine();
				
				
				
				
				//////////////////////////////////////
				
				
				
			/*preparación de variables para pasarle  a la tarea*/
				
			
				Map<String, Object> variablesEntrada = new HashMap<String, Object>();
				variablesEntrada.put("documento", document);
				variablesEntrada.put("tipoPoliza", tipoPoliza);
				variablesEntrada.put("intermediario", intermediario);
				
			///////////////////////////////////
				/* iniciar ejecución del proceso */
				
								
				RuntimeService runtimeService = processEngine.getRuntimeService();
				
					ProcessInstance processInstance =
					runtimeService.startProcessInstanceByKey("a1001",variablesEntrada );
				
				
				
	
	}

}
