package com.hjy.tim.client.service.impl.command;

import com.hjy.tim.client.service.EchoService;
import com.hjy.tim.client.service.InnerCommand;
import com.hjy.tim.client.service.MsgHandle;
import com.hjy.tim.common.data.construct.RingBufferWheel;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @since JDK 1.8
 */
@Service
public class DelayMsgCommand implements InnerCommand {

    @Autowired
    private EchoService echoService ;

    @Autowired
    private MsgHandle msgHandle ;

    @Autowired
    private RingBufferWheel ringBufferWheel ;

    @Override
    public void process(String msg) {
        if (msg.split(" ").length <=2){
            echoService.echo("incorrect commond, :delay [msg] [delayTime]") ;
            return ;
        }

        String message = msg.split(" ")[1] ;
        Integer delayTime = Integer.valueOf(msg.split(" ")[2]);

        RingBufferWheel.Task task = new DelayMsgJob(message) ;
        task.setKey(delayTime);
        ringBufferWheel.addTask(task);
        echoService.echo(EmojiParser.parseToUnicode(msg));
    }



    private class DelayMsgJob extends RingBufferWheel.Task{

        private String msg ;

        public DelayMsgJob(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            msgHandle.sendMsg(msg);
        }
    }
}
