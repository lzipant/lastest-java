package net.ant.lzip.reactive.flow;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * @author harrisonlee
 * @date 3/13/23 17:37
 * @tag
 * @description TODO
 */
public class FlowApiTest {
    public static void main(String[] args) throws InterruptedException {
        SubmissionPublisher<String> publisher = new SubmissionPublisher();
        publisher.subscribe(cmccSubscriber());

        for (int i = 0; i < 10; i++) {
            publisher.submit("123");
        }

        publisher.close();

        Thread.sleep(2000);

    }

    public static Flow.Subscriber<String> cmccSubscriber() {
        return new Flow.Subscriber<String>() {

            Flow.Subscription subscription;

            final int requestSize = 1;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                System.out.println("cmcc subscriber subscribe");
                this.subscription = subscription;

                this.subscription.request(requestSize); // fire request
            }

            @Override
            public void onNext(String item) {
                System.out.println(" cmcc subscriber get: " + item); // process item
                this.subscription.request(requestSize); // request more
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("cmcc subscriber error");
            }

            @Override
            public void onComplete() {
                System.out.println("cmcc subscriber complete");
            }
        };
    }
}
