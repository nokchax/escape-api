package com.nokchax.escape.command;

import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.service.UserService;
import org.telegram.telegrambots.meta.api.objects.Message;

public class RegisterCommand extends Command<UserService>{
    public RegisterCommand(Message message) {
        super(message);
        sudo = true;
    }

    // id, pw, name
    @Override
    public String internalProcess() throws Exception {
        User user;
        try {
            user = new User(getOptions().get("u"), getOptions().get("p"), getOptions().get("n"));
        } catch (Exception e) {
            return "parameter is not correct, command like below form\n" +
                    "/register -u {id} -p {password} -n {name}";
        }
        processor().registerUser(user);

        return "Register complete";
    }
}
