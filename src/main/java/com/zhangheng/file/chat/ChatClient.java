package com.zhangheng.file.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class ChatClient {

    private String ip;//服务端IP
    private int port;//服务端端口

    public ChatClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
    public void run() {
        EventLoopGroup group = new NioEventLoopGroup(1);
        try {
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline=ch.pipeline();
                            //向pipeline加入解码器
                            pipeline.addLast("decoder",new StringDecoder());
                            //向pipeline加入编码器
                            pipeline.addLast("encoder",new StringEncoder());
                            pipeline.addLast(new ChatClientHandler());
                        }
                    });

            ChannelFuture cf = bootstrap.connect(ip, port).sync();
            Channel channel = cf.channel();
            System.out.println("=====客户端"+channel.localAddress()+" 启动成功=====\n");
            //客户端输入信息，创建一个扫描器
            Scanner scanner=new Scanner(System.in);
            while (scanner.hasNextLine()){
                String msg=scanner.nextLine();
                channel.writeAndFlush(msg);
            }
        } catch (Exception e) {
            if (e.getMessage().startsWith("Connection refused")){
                System.out.println("连接拒绝："+e.getMessage());
            }else {
                System.out.println("错误："+e.getMessage());
            }
        } finally {
            group.shutdownGracefully();
        }

    }
}
