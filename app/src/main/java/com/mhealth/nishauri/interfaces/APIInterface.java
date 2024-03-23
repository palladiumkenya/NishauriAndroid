package com.mhealth.nishauri.interfaces;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import com.mhealth.nishauri.Models.BotChatMessage;
import com.mhealth.nishauri.Models.ChatBotQuestion;

public interface APIInterface {
    @POST("nishauri/chat")
    Call<BotChatMessage> sendMessage(@Body ChatBotQuestion message);
}
