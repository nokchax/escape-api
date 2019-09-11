package com.zum.escape.api.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Service
public class AdminService {
    @Value("${observer.admins}")
    private List<Integer> ADMIN_LIST;

    public void showAdminList() {
        System.out.println(ADMIN_LIST);
    }

    public String byPassMessage(Message message) {
        if(!isAdmin(message))
            return "Permission Denied";

        return "";
    }


    private boolean isAdmin(Message message) {
        Integer userId = message.getFrom().getId();

        return ADMIN_LIST.contains(userId);
    }
}
