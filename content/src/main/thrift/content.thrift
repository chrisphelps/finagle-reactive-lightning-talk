namespace java com.sutemi.example.content

struct Content {
    1: optional string title;
    2: optional string description;
    3: optional string customtext;
}

service ContentRpc {
  Content getContent();
}