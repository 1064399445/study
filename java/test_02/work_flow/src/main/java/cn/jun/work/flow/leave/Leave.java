package cn.jun.work.flow.leave;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 第一个流程运行类
 * @author  jeremy king
 *
 */
public class Leave {

    public static void main(String[] args) {
        // 创建流程引擎，获取默认的流程引擎
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 得到流程存储服务
        RepositoryService repositoryService = engine.getRepositoryService();
        // 得到运行时服务
        RuntimeService runtimeService = engine.getRuntimeService();
        // 获取流程任务
        TaskService taskService = engine.getTaskService();
        // 部署流程文件
        repositoryService.createDeployment()
                .addClasspathResource("leave.xml").deploy();
        // 开启流程
        runtimeService.startProcessInstanceByKey("myProcess_1");
        // 查询第一个节点的任务并且输出
        Task task = taskService.createTaskQuery().singleResult();
        System.out.println("第一个任务完成前，当前任务名称：" + task.getName());
        // 完成第一个任务，相当于流程图中的请假申请
        taskService.complete(task.getId());
        // 查询第二个节点的任务并且输出
        task = taskService.createTaskQuery().singleResult();
        System.out.println("第二个任务完成前，当前任务名称：" + task.getName());
        // 完成第二个任务，相当于流程图中的请假审核（流程结束）
        taskService.complete(task.getId());
        task = taskService.createTaskQuery().singleResult();
        System.out.println("流程结束后，查找任务：" + task);
        ConcurrentHashMap hashMap;
    }
}


