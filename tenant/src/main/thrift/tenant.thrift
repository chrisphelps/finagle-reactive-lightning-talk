namespace java com.sutemi.example.tenant

struct Tenant {
    1: optional string name;
    2: optional string id;
}

service TenantRpc {
  Tenant getTenant();
}