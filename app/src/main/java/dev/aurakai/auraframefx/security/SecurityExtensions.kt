package dev.aurakai.auraframefx.security

import dev.aurakai.auraframefx.model.AgentRequest

/**
 * Extended Security Context with Request Validation
 */
fun SecurityContext.validateRequest(requestType: String, requestData: String) {
    if (!validateAccess(requestType)) {
        throw SecurityException("Access denied for request type: $requestType")
    }
    // Additional validation logic here
}