package com.zhangheng.file.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


public class ChatServer {

    private int port;//端口号
    private int maxnum=50;//默认最大连接数
    private Logger log = LoggerFactory.getLogger(getClass());

    public ChatServer(int port) {
        this.port = port;
    }

    public ChatServer(int port, int maxnum) {
        this.port = port;
        this.maxnum = maxnum;
    }

    public void run(){
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(maxnum);
        try {
            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline=ch.pipeline();
                            //向pipeline加入解码器
                            pipeline.addLast("decoder",new StringDecoder(CharsetUtil.UTF_8));
                            //向pipeline加入编码器
                            pipeline.addLast("encoder",new StringEncoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new ChatServerHandler());
                        }
                    });
            ChannelFuture cf=bootstrap.bind(port).sync();
            Channel channel = cf.channel();
            String s = channel.localAddress().toString();
//            System.out.println("聊天服务端启动成功！端口："+s.substring(s.lastIndexOf(":")));
            log.info("聊天服务器启动成功！聊天服务器本地端口"+s.substring(s.lastIndexOf(":")));
            cf.channel().closeFuture().sync();
        }catch (Exception e){
            System.out.println("服务端错误："+e.getMessage());
            log.error("聊天服务端错误："+e.getMessage());
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
