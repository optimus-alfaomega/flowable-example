package org.flowable.script;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.slf4j.LoggerFactory;


public class FlowScriptJob {

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
		  .addClasspathResource("simpleProcess.bpmn20.xml")
		  .deploy();
		 
		 
		 RuntimeService runtimeService = processEngine.getRuntimeService();
				 LocalDateTime dueDate = LocalDate.now().atStartOfDay().plus(3, ChronoUnit.DAYS);
				 Map<String, Object> variables = new HashMap<String, Object>();
				 LoggerFactory.getLogger("INPUT TIME").info("dueDate: {}", dueDate);
				 variables.put("targetDate", dueDate.toString());
				 ProcessInstance processInstance1 = runtimeService.startProcessInstanceByKey("dueDatePlayground", variables);
				
		/////////////////////////// lado de atención del asesor
	
			TaskService taskService = processEngine.getTaskService();				 
			List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance1.getId()).list();
			LoggerFactory.getLogger("TASK SIZE").info("size {}", tasks.size());
				
			Task firstTask = tasks.get(0);
			
			LoggerFactory.getLogger("sample").info("dueDate: {}", firstTask.getDueDate());
			
			taskService.complete(firstTask.getId());
			
			
	}

}
