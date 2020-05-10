package com.coursepoject.controller;

import com.coursepoject.data.Topic;
import com.coursepoject.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TopicController {

    @Autowired
    private TopicService topicService;

    @RequestMapping("/topics")
    public List<Topic> getAllTopics(){
        return topicService.getAllTopics();
    }


    @RequestMapping("/topics/{id}")
    public Topic getTopic(@PathVariable String id) {
        return topicService.getTopic(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/topics")
    public List<Topic> addTopic(@RequestBody Topic topic){
        return topicService.addTopic(topic);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/topics/{id}")
    public List<Topic> deleteTopic(@PathVariable String id){
        return topicService.deleteTopic(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/topics/{id}")
    public List<Topic> updateTopic(@RequestBody Topic topic, @PathVariable String id){
        return topicService.updateTopic(topic,id);
    }
}
