package com.zhangheng.file.chat.ShopChat;

import com.google.gson.Gson;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.add(channel);
        Gson gson = new Gson();
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setFrom("1");
        chatInfo.setFrom_name("服务器");
        chatInfo.setTo("all");
        chatInfo.setTo_name("全部");
        chatInfo.setMsgType(2);
        chatInfo.setChatType(1);
        chatInfo.setTime(sdf.format(new Date()));
        String message="<"+channel.remoteAddress().toString().replace("/127.0.0.1:","")
                +">上线了\n当前在线人数："+channelGroup.size();
        chatInfo.setMessage(message);
        String json = gson.toJson(chatInfo);
        //将该客户加入聊天信息推送给其他在线的客户端
        //该方法会将channelGroup中的所有channel遍历，并发送消息


        channelGroup.writeAndFlush(json);

//             channelGroup.writeAndFlush(sdf.format(new Date())+"--[客户端]<"
//                     +channel.remoteAddress().toString().replace("/127.0.0.1:","")
//                     +">上线了\n当前在线人数："+channelGroup.size()+"\n");

        //将当前channel加入到channelGroup中
//        channelGroup.add(channel);
//        System.out.println(sdf.format(new Date())+"--[客户端]<"
//                +channel.remoteAddress().toString().replace("/127.0.0.1:","")
//                +">上线了\n当前在线人数："+channelGroup.size()+"\n");
        log.info("购物APP聊天服务器--[客户端]<"
                +channel.remoteAddress().toString().replace("/127.0.0.1:","")
                +">上线了 \t 当前在线人数："+channelGroup.size());

    }

/*    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf= (ByteBuf) msg;
        String s = buf.toString(CharsetUtil.UTF_8);
        final Channel channel = ctx.channel();
        for (Channel ch : channelGroup) {
            if (channel!=ch){
                ch.writeAndFlush(sdf.format(new Date())+"[客户端]"+channel.remoteAddress()+"发送消息："+s+"\n");
            }else {
                ch.writeAndFlush(sdf.format(new Date())+"[自己]发送消息："+s+"\n");
            }
        }
    }*/

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        super.channelInactive(ctx);
        Channel channel = ctx.channel();
        Gson gson = new Gson();
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setFrom("1");
        chatInfo.setFrom_name("服务器");
        chatInfo.setTo("all");
        chatInfo.setTo_name("全部");
        chatInfo.setMsgType(2);
        chatInfo.setChatType(1);
        chatInfo.setTime(sdf.format(new Date()));
        String message="<"+channel.remoteAddress().toString().replace("/127.0.0.1:","")
                +">下线了\n当前在线人数："+channelGroup.size();
        chatInfo.setMessage(message);
        String json = gson.toJson(chatInfo);

        channelGroup.writeAndFlush(json);
//        System.out.println(sdf.format(new Date())+"--[客户端]<"
//                +channel.remoteAddress().toString().replace("/127.0.0.1:","")
//                +">下线了\n当前在线人数："+channelGroup.size()+"\n");
        log.info("购物APP聊天服务器--[客户端]<"
                +channel.remoteAddress().toString().replace("/127.0.0.1:","")
                +">下线了 \t 当前在线人数："+channelGroup.size());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
//        cause.printStackTrace();
        if (cause.getMessage().indexOf("远程主机强迫关闭了一个现有的连接")>=0){

        }else {
//            System.out.println("服务器错误："+ cause.getMessage());
            log.error("购物APP聊天服务器错误："+ cause.getMessage());
        }
        ctx.close();
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
//        Gson gson = new Gson();
//        ChatInfo chatInfo = gson.fromJson(s, ChatInfo.class);
//        Channel channel = channelHandlerContext.channel();
        ChatInfo chat = new Gson().fromJson(s, ChatInfo.class);
        /*for (Channel ch : channelGroup) {
            ch.writeAndFlush(s);
        }*/
        if (chat.getFrom().length()==11&&chat.getMessage().equals("系统0805:我上线了")){
            Gson gson = new Gson();
            ChatInfo chatInfo = new ChatInfo();
            chatInfo.setFrom("1");
            chatInfo.setFrom_name("服务器");
            chatInfo.setTo("all");
            chatInfo.setTo_name("全部");
            chatInfo.setMsgType(2);
            chatInfo.setChatType(1);
            chatInfo.setTime(sdf.format(new Date()));
            String message="用户《"+chat.getFrom_name()+"》<"+chat.getFrom()+">进入聊天室了";
            chatInfo.setMessage(message);
            String s1 = gson.toJson(chatInfo);
            for (Channel ch : channelGroup) {
                ch.writeAndFlush(s1);
            }
            log.info(message);
        }else if (chat.getFrom().length()==11&&chat.getMessage().equals("系统0805:我下线了")){
            Gson gson = new Gson();
            ChatInfo chatInfo = new ChatInfo();
            chatInfo.setFrom("1");
            chatInfo.setFrom_name("服务器");
            chatInfo.setTo("all");
            chatInfo.setTo_name("全部");
            chatInfo.setMsgType(2);
            chatInfo.setChatType(1);
            chatInfo.setTime(sdf.format(new Date()));
            String message="用户《"+chat.getFrom_name()+"》<"+chat.getFrom()+">退出聊天室了";
            chatInfo.setMessage(message);
            String s1 = gson.toJson(chatInfo);
            for (Channel ch : channelGroup) {
                ch.writeAndFlush(s1);
            }
            log.info(message);
        }
        else {
            for (Channel ch : channelGroup) {
                ch.writeAndFlush(s);
            }
        }
        /*if (chatInfo.getTo().equals("all")) {
            for (Channel ch : channelGroup) {
                if (channel != ch) {
                    ch.writeAndFlush(chatInfo.getTime() + "--["
                            + chatInfo.getFrom()
                            + "]：" + chatInfo.getMessage() + "\n");
                } else {
                    ch.writeAndFlush(chatInfo.getTime() + "--[自己]：" + chatInfo.getMessage() + "\n");
                }
            }
        }*/
        /*for (Channel ch : channelGroup) {
            if (channel != ch) {
                ch.writeAndFlush(sdf.format(new Date()) + "--["
                        + channel.remoteAddress().toString().replace("/127.0.0.1:", "")
                        + "]：" + s + "\n");
            } else {
                ch.writeAndFlush(sdf.format(new Date()) + "--[自己]：" + s + "\n");
            }
        }*/
    }
}
