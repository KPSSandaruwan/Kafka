package com.suraja;

import com.google.common.collect.Lists;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TwitterProducer {

    public TwitterProducer(){}

    public static void main(String[] args) {
        new TwitterProducer().run();
    }

    public void run () {
        // Create a twitter producer

        // Create a kafka producer

        // Loop to send tweets to kafka
    }

    String consumerKey = "nb8Z6oIeLkng55ReRGWUjNiGT";
    String consumerSecret = "pPjHyyboURAfpSulBfgbasfz76MjSQm4RsOHhKW3EuUkePhPPp";
    String token = "";
    String secret = "AAAAAAAAAAAAAAAAAAAAAJomKwEAAAAAE0d8snJJQGRqnSi1qDHomaFfbsk%3D7314I4x4lJeKktxyTdzfV0qhgKyf9Edi6v3efVPJTizSihvKAX";



    public void createTwitterClient() {
        /** Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream */
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100000);


        /** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
        // Optional: set up some followings and track terms
        List<String> terms = Lists.newArrayList("kafka"); // Following terms
        hosebirdEndpoint.trackTerms(terms);

        // These secrets should be read from a config file
        Authentication hosebirdAuth = new OAuth1("consumerKey", "consumerSecret", "token", "secret");
    }
}
