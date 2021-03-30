package com.zhangheng.file.chat;

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
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将该客户加入聊天信息推送给其他在线的客户端
        //该方法会将channelGroup中的所有channel遍历，并发送消息

        channelGroup.add(channel);

             channelGroup.writeAndFlush(sdf.format(new Date())+"--[客户端]<"
                     +channel.remoteAddress().toString().replace("/127.0.0.1:","")
                     +">上线了\n当前在线人数："+channelGroup.size()+"\n");


        //将当前channel加入到channelGroup中
//        channelGroup.add(channel);
//        System.out.println(sdf.format(new Date())+"--[客户端]<"
//                +channel.remoteAddress().toString().replace("/127.0.0.1:","")
//                +">上线了\n当前在线人数："+channelGroup.size()+"\n");
        log.info("聊天服务器--[客户端]<"
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
        channelGroup.writeAndFlush(sdf.format(new Date())+"--[客户端]<"
                +channel.remoteAddress().toString().replace("/127.0.0.1:","")
                +">下线了\n当前在线人数："+channelGroup.size()+"\n");
//        System.out.println(sdf.format(new Date())+"--[客户端]<"
//                +channel.remoteAddress().toString().replace("/127.0.0.1:","")
//                +">下线了\n当前在线人数："+channelGroup.size()+"\n");
        log.info("聊天服务器--[客户端]<"
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
            log.error("聊天服务器错误："+ cause.getMessage());
        }
        ctx.close();
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel channel = channelHandlerContext.channel();
        for (Channel ch : channelGroup) {
            if (channel!=ch){
                ch.writeAndFlush(sdf.format(new Date())+"--["
                        +channel.remoteAddress().toString().replace("/127.0.0.1:","")
                        +"]："+s+"\n");
            }else {
                ch.writeAndFlush(sdf.format(new Date())+"--[自己]："+s+"\n");
            }
        }
    }
}
