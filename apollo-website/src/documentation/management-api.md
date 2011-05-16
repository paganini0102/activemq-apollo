# Apollo ${project_version} Management REST API

{:toc:2-5}

## Overview

Apollo's REST API runs on port 61680 by default.   If your running 
an Apollo broker on your local machine, you  would access the 
API at the following HTTP URL:

    https://localhost:61680

For all of the rest of this document, we will be leaving off that part,
since it is the same for every API call.

### Authentication

The broker requires all requests against the management API to supply
user credentials which have administration privileges.

The user credentials should be supplied using via HTTP basic
authentication. Example:

    $ curl -u "admin:password" http://localhost:61680

### JSON Representation

The API routes are intended to be access programmatically as JSON
services but they also provide an HTML representation so that the API
services can easily be browsed using a standard web browser.

You must set the HTTP `Accept` header to `application/json` to get the
json representation of the data. Example:

    $ curl -H "Accept: application/json" -u "admin:password" \
    http://localhost:61680

### Broker Management

The route for managing the broker is:

    /

Doing a GET against it will provide information about the broker's

  * Version
  * Running State
  * Virtual Hosts
  * Connectors
  * Connections
  
Example:

    $ curl -H "Accept: application/json" -u "admin:password" \
    http://localhost:61680/

Results in:

{pygmentize:: js}
{
  "version":"${project_version}",
  "id":"default",
  "state":"STARTED",
  "state_since":1305388053086,
  "current_time":1305388817653,
  "virtual_hosts":["localhost"],
  "connectors":[
    "stomps",
    "stomp"
  ],
  "connections":[
    {"id":24,"label":"/127.0.0.1:52603"},
    {"id":25,"label":"/127.0.0.1:52604"}
  ],
}
{pygmentize}

### Connector Management

The route for managing a connector is:

    /connectors/:id

Example:

    $ curl -H "Accept: application/json" -u "admin:password" \
    http://localhost:61680/connectors/stomp

Results in:

{pygmentize:: js}
{
  "id":"stomp",
  "state":"STARTED",
  "state_since":1305553109899,
  "accepted":6,
  "connections":[
    {"id":5,"label":"/127.0.0.1:52638"},
    {"id":6,"label":"/127.0.0.1:52639"}
  ]
}
{pygmentize}

### Connection Management

The route for managing a connection is:

    /connections/:id

Example:

    $ curl -H "Accept: application/json" -u "admin:password" \
    http://localhost:61680/connections/5

Results in:

{pygmentize:: js}
{
  "id":"5",
  "state":"STARTED",
  "state_since":1305553686946,
  "read_counter":1401476017,
  "write_counter":99,
  "transport":"tcp",
  "protocol":"stomp",
  "remote_address":"/127.0.0.1:52638",
  "protocol_version":"1.0",
  "user":"admin",
  "waiting_on":"client request",
  "subscription_count":0
}
{pygmentize}

To shutdown a connection send a POST to:

    /connections/:id/action/shutdown

Example:

    curl -X POST -H "Accept: application/json" -u "admin:password" \
    http://localhost:61680/connections/5/action/shutdown


### Virtual Host Management

The route for managing a virtual host is:

    /virtual-hosts/:name

Where `:name` is the id of a virtual host configured in the broker.
Example:

    $ curl -H "Accept: application/json" -u "admin:password" \
    http://localhost:61680/virtual-hosts/localhost

Results in:

{pygmentize:: js}
{
  "id":"localhost",
  "state":"STARTED",
  "state_since":1305390871786,
  "topics":[
    {"id":6,"label":"item.prices"},
    {"id":3,"label":"inventory.level"}
  ],
  "queues":[
    {"id":10,"label":"orders.req"},
    {"id":8,"label":"orders.res"},
  ],
  "store":true
}
{pygmentize}

#### Virtual Host Store Management

The route for managing a virtual host's Store is:

    /virtual-hosts/:name/store

Where `:name` is the id of a virtual host configured in the broker.

Example:

    $ curl -H "Accept: application/json" -u "admin:password" \
    http://localhost:61680/virtual-hosts/localhost/store

Results in:

{pygmentize:: js}
{
  "@class":"org.apache.activemq.apollo.broker.store.bdb.dto.BDBStoreStatusDTO",
  "state":"STARTED",
  "state_since":1305554120954,
  "canceled_message_counter":87927,
  "flushed_message_counter":28576,
  "canceled_enqueue_counter":87927,
  "flushed_enqueue_counter":28576,
  "message_load_latency":{
    "count":0,
    "total":0,
    "max":0,
    "min":0
  },
  "flush_latency":{
    "count":0,
    "total":0,
    "max":0,
    "min":0
  },
  "journal_append_latency":null,
  "index_update_latency":null,
  "message_load_batch_size":{
    "count":0,
    "total":0,
    "max":-2147483648,
    "min":2147483647
  },
  "pending_stores":0
}
{pygmentize}

#### Queue Management

The route for managing a virtual host's Queue is:

    /virtual-hosts/:name/queues/:qid

Where `:name` is the id of a virtual host configured in the broker and `:qid` is the id
of the queue.

Example:

    $ curl -H "Accept: application/json" -u "admin:password" \
    http://localhost:61680/virtual-hosts/localhost/queues/1

Results in:

{pygmentize:: js}
{
  "id":1,
  "config":{
    "name":null,
    "unified":null,
    "producer_buffer":null,
    "queue_buffer":null,
    "consumer_buffer":null,
    "persistent":null,
    "swap":null,
    "swap_range_size":null,
    "acl":null
  },
  "destination":{
    "@class":"org.apache.activemq.apollo.dto.QueueDestinationDTO",
    "name":"load-0"
  },
  "metrics":{
    "enqueue_item_counter":37828413,
    "enqueue_size_counter":41713628436,
    "enqueue_ts":1305554574681,
    "dequeue_item_counter":37828413,
    "dequeue_size_counter":41713628436,
    "dequeue_ts":1305554574681,
    "nack_item_counter":0,
    "nack_size_counter":0,
    "nack_ts":1305554121093,
    "queue_size":0,
    "queue_items":0,
    "swapped_in_size":0,
    "swapped_in_items":0,
    "swapping_in_size":0,
    "swapping_out_size":0,
    "swapped_in_size_max":557056,
    "swap_out_item_counter":16,
    "swap_out_size_counter":17634,
    "swap_in_item_counter":16,
    "swap_in_size_counter":17634
  },
  "entries":[],
  "producers":[
    {
      "kind":"connection",
      "ref":"1",
      "label":"/127.0.0.1:52690"
    }
  ],"consumers":[
    {
      "link":{
        "kind":"connection",
        "ref":"2",
        "label":"/127.0.0.1:52691"
      },
      "position":37828414,
      "acquired_count":0,
      "acquired_size":0,
      "total_dispatched_count":37828413,
      "total_dispatched_size":41713628436,
      "total_ack_count":37828413,
      "total_nack_count":0,
      "waiting_on":"producer"
    }
  ]
}
{pygmentize}


#### Topic Management

The route for managing a virtual host's Topic is:

    /virtual-hosts/:name/topics/:tid

Where `:name` is the id of a virtual host configured in the broker and `:tid` is the id
of the topic.

Example:

    $ curl -H "Accept: application/json" -u "admin:password" \
    http://localhost:61680/virtual-hosts/localhost/topics/1

Results in:

{pygmentize:: js}
{
  "id":1,
  "name":"load-0",
  "config":{
    "name":null,
    "slow_consumer_policy":null,
    "acl":null
  },
  "producers":[
    {
      "kind":"connection",
      "ref":"3",
      "label":"/127.0.0.1:52772"
    }
  ],
  "consumers":[
    {
      "kind":"connection",
      "ref":"4",
      "label":"/127.0.0.1:52773"
    }
  ],
  "durable_subscriptions":[
  ]
}
{pygmentize}

### Getting the Broker's Configuration

To get current runtime configuration of the broker GET:

    /config/runtime

Example:

    $ curl -H "Accept: application/json" -u "admin:password" \
    http://localhost:61680/config/runtime

Results in:

{pygmentize:: js}
{
  "notes":"\n    The default configuration with tls/ssl enabled.\n  ",
  "virtual_hosts":[{
    "id":"apollo-a",
    "enabled":null,
    "host_names":["localhost"],
    "store":{
      "@class":"org.apache.activemq.apollo.broker.store.bdb.dto.BDBStoreDTO",
      "flush_delay":null,
      "directory":"/Users/chirino/opt/apollo-a/data",
      "read_threads":null,
      "zero_copy":null
    },
    "auto_create_destinations":null,
    "purge_on_startup":null,
    "topics":[],
    "queues":[],
    "durable_subscriptions":[],
    "regroup_connections":null,
    "acl":{
      "connects":[
        {"allow":"admins","deny":null,"kind":null}
      ]
    },
    "authentication":{
      "enabled":false,
      "domain":null,
      "acl_principal_kinds":[],
      "user_principal_kinds":[]
    },
    "router":null,
    "log_category":null
  }],
  "connectors":[{
      "id":"stomp",
      "enabled":null,
      "bind":"tcp://0.0.0.0:61613",
      "protocol":null,
      "connection_limit":2000,
      "protocols":[],"acl":null
    },{
      "id":"stomps",
      "enabled":null,
      "bind":"tls://0.0.0.0:61614",
      "protocol":null,
      "connection_limit":2000,
      "protocols":[],
      "acl":null
    }],
  "client_address":null,
  "key_storage":{
    "file":"/opt/apollo-a/etc/keystore",
    "password":null,
    "key_password":null,
    "store_type":null,
    "trust_algorithm":null,
    "key_algorithm":null
  },
  "acl":{
    "admins":[{
      "allow":"admins",
      "deny":null,
      "kind":null
    }]
  },
  "web_admin":{
    "bind":"http://127.0.0.1:61680"
  },
  "authentication":{
    "enabled":null,
    "domain":"apollo",
    "acl_principal_kinds":[],
    "user_principal_kinds":[]
  },
  "log_category":{
    "console":"console",
    "audit":"audit",
    "connection":"connection",
    "security":"security"
  },
  "sticky_dispatching":null
}
{pygmentize}
      
### Aggregate Queue Statistics

You can get aggregate queue statistics at either the broker or virtual host level by
using one of the following URL routes:

    /queue-metrics
    /virtual-hosts/:name/queue-metrics

Example:

    $ curl -H "Accept: application/json" -u "admin:password" \
    http://localhost:61680/queue-metrics

Results in:

{pygmentize:: js}
{
  "enqueue_item_counter":0,
  "enqueue_size_counter":0,
  "enqueue_ts":0,
  "dequeue_item_counter":0,
  "dequeue_size_counter":0,
  "dequeue_ts":0,
  "nack_item_counter":0,
  "nack_size_counter":0,
  "nack_ts":0,
  "queue_size":0,
  "queue_items":0,
  "swapped_in_size":0,
  "swapped_in_items":0,
  "swapping_in_size":0,
  "swapping_out_size":0,
  "swapped_in_size_max":0,
  "swap_out_item_counter":0,
  "swap_out_size_counter":0,
  "swap_in_item_counter":0,
  "swap_in_size_counter":0,
  "queues":0
}
{pygmentize}