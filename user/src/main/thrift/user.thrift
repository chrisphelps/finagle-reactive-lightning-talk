namespace java com.sutemi.example.user

struct User {
    1: optional string name;
    2: optional string email;
}

service UserRpc {
  User getUser();
}