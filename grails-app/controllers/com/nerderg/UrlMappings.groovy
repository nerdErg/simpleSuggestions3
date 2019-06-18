package com.nerderg

class UrlMappings {

    static mappings = {
        "/suggest/$subject"(controller: 'suggest', action: 'suggest')
    }
}
