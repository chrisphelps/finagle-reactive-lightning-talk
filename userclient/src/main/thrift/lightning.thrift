namespace java com.sutemi.example.lightning

struct Content {
    1: optional string title;
    2: optional string description;
    3: optional string customtext;
    4: optional string tenantId;
}

struct Tenant {
    1: optional string name;
    2: optional string id;
}

struct User {
    1: optional string name;
    2: optional string email;
}

service ContentRpc {
  Content getContent(1: Tenant tenant);
}

service TenantRpc {
  Tenant getTenant();
}

service UserRpc {
  User getUser();
}

service PersonalizationRpc {
    Content personalizeContent(1: Content incontent, 2: User user);
}