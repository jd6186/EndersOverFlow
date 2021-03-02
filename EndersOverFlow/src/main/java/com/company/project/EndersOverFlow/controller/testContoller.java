package com.company.project.EndersOverFlow.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;

import com.contentstack.sdk.ContentType;
import com.contentstack.sdk.Contentstack;
import com.contentstack.sdk.Entry;
import com.contentstack.sdk.EntryResultCallBack;
import com.contentstack.sdk.Error;
import com.contentstack.sdk.Query;
import com.contentstack.sdk.QueryResult;
import com.contentstack.sdk.QueryResultsCallBack;
import com.contentstack.sdk.ResponseType;
import com.contentstack.sdk.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.company.project.EndersOverFlow.model.CodeReview;
import com.company.project.EndersOverFlow.model.Member;

@Controller
@RequestMapping("testPage")
public class testContoller {

	@Autowired
	com.company.project.EndersOverFlow.service.MemberService memberService;

	// 코드리뷰 리스트 전체 목록 페이지로 이동
	@RequestMapping("main")
	public ModelAndView goPage(@ModelAttribute Member member,
            HttpServletRequest request) throws Exception {
		
		// 1. Stack 접근
		// American Region
		Stack stack = Contentstack.stack("blt764fed72e3e83b7f", "cs906789bd8b7f064cf1cc2262", "test_environment");
		
		// European Region
		/*
		Config config = Config();
		Config.region = ContentstackRegion.EU;
		Stack stack = Contentstack.stack("stack_api_key", "delivery_token", "environment_name", config);
		*/
		
		
		// 2. ContentType 접근
		// 단순 연결 여부 조회
		ContentType contentType = stack.contentType("singleentry");
		Entry blogEntry = contentType.entry("blt9e1065db8f527104");
		blogEntry.fetch(new EntryResultCallBack() {
		    @Override
		    public void onCompletion(ResponseType responseType, Error error) {
		        if (error == null) {
		            System.out.println("Single Entry 정상 연결");
		            System.out.println(responseType);
		        } else {
		            System.out.println("Single Entry 연결 실패");
		            System.out.println(error);
		        }
		    }
		});
		
		// Entry를 다양하게 만들어 놨을 경우 (title도 있고 url도 있고 등등등)
		Query blogQuery = stack.contentType("singleentry").query();
		blogQuery.find(new QueryResultsCallBack() {
		    @Override
		    public void onCompletion(ResponseType responseType, QueryResult queryResult, Error error) {
		        if (error == null) {
		            System.out.println("Multiple Entries 정상 연결");
		            System.out.println(responseType);
		            List<Entry> result = queryResult.getResultObjects();
                    for (Entry entry : result){
                        try {
							System.out.println("Title: " + entry.toJSON());
							System.out.println("Title: " + entry.toJSON().getString("title"));
							System.out.println("URL: " + entry.toJSON().getString("url"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }
		        } else {
		            System.out.println("Multiple Entries 연결 실패");
		            System.out.println(error);
		        }
		    }
		});
		
		/*
		// Multiple Entries가 100가지가 넘을 경우
		Query csQuery = stack.contentType("contentType_name").query();
		csQuery.skip(20);
		csQuery.limit(20);
		csQuery.find(new QueryResultsCallBack() {

		          @Override
		          public void onCompletion(ResponseType responseType, QueryResult queryResult, Error error) {
		          }
		      });
		*/
		
		
		return new ModelAndView("member_page/Login");
	}
}
