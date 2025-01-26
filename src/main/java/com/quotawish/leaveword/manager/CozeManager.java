package com.quotawish.leaveword.manager;

import com.coze.openapi.client.workflows.run.RunWorkflowReq;
import com.coze.openapi.client.workflows.run.model.WorkflowEvent;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.config.Consts;
import com.coze.openapi.service.service.CozeAPI;
import com.quotawish.leaveword.config.CozeConfig;
import io.reactivex.Flowable;
import lombok.Getter;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

@Getter
@Component
public class CozeManager {

    @Resource
    private CozeConfig config;

    private TokenAuth authCli;

    private CozeAPI cozeApi;

    @PostConstruct
    public void init() {
        authCli = new TokenAuth(config.getPat());
        this.cozeApi = new CozeAPI.Builder()
                .baseURL(Consts.COZE_CN_BASE_URL)
                .auth(authCli)
                .client(new OkHttpClient.Builder().build())
                .build();
    }

    public Flowable<WorkflowEvent> startWorkflow(String id, Map<String, Object> data) {
        RunWorkflowReq req = RunWorkflowReq.builder().workflowID(id).parameters(data).build();

        return this.cozeApi.workflows().runs().stream(req);
    }
}
