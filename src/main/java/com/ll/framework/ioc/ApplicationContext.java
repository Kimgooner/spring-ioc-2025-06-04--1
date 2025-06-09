package com.ll.framework.ioc;

import com.ll.domain.testPost.testPost.repository.TestPostRepository;
import com.ll.domain.testPost.testPost.service.TestFacadePostService;
import com.ll.domain.testPost.testPost.service.TestPostService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ApplicationContext {
    private Map<String, Object> beans = new HashMap<>(); // 싱글톤을 보장하기 위함
    public ApplicationContext() {
    }

    public <T> T genBean(String beanName) {
        if(beans.containsKey(beanName)){ // 이미 생성된 빈인지 확인
            return (T) beans.get(beanName);
        }

        Object instance = null;

        switch (beanName){ // 빈 생성 하드 코딩
            case "testPostRepository" -> {
                instance = new TestPostRepository();
            }

            case "testPostService" -> {
                TestPostRepository testPostRepository = genBean("testPostRepository");
                instance = new TestPostService(testPostRepository);
            }

            case "testFacadePostService" -> {
                TestPostRepository testPostRepository = genBean("testPostRepository");
                TestPostService testPostService = genBean("testPostService");
                instance = new TestFacadePostService(testPostService, testPostRepository);
            }

            default -> System.out.println("등록되지 않은 빈입니다.");
        }

        beans.put(beanName, instance);
        return (T) instance;
    }

    public String getBeanName(String beanName){
        return beanName.substring(0,1).toUpperCase() + beanName.substring(1);
    }
}
