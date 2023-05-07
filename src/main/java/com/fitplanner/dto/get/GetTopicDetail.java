package com.fitplanner.dto.get;

import java.util.List;

public class GetTopicDetail {
    public String title;
    public List<GetTopicDetailComment> comments;

    public GetTopicDetail() {
        // TODO Auto-generated constructor stub
    }

    public GetTopicDetail(String title, List<GetTopicDetailComment> comments) {
        super();
        this.title = title;
        this.comments = comments;
    }

}
