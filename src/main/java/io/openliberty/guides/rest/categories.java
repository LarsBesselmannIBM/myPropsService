// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2017, 2022 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
// end::copyright[]
package io.openliberty.guides.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;


// tag::path[]
@Path("categories")
// end::path[]

public class categories {

    // get the list of categories supported by the service
    @GET
    @Path("categories")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "404",
        description = "Missing description",
        content = @Content(mediaType = "application/json"))
    @APIResponseSchema(value = Properties.class,
        responseDescription = "List of categories supported by the service",
        responseCode = "200")
    @Operation(
        summary = "Get the list of supported categories",
        description = "Retrieves and returns the list of supported categories")

    public Response getCategory() {
        
        Properties categories = new Properties();
            categories.put("capitals", "List of capitals");
            categories.put("system", "List of system properties");
            categories.put("team", "List of people in the team");
            return Response.ok(categories).build();
      
    }

    // Get the list of properties for one of the supported categories
    @GET
    @Path("/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "404",
        description = "Missing description",
        content = @Content(mediaType = "application/json"))
    @APIResponseSchema(value = Properties.class,
        responseDescription = "Properties of a particular category, choose category system for JVM system properties",
        responseCode = "200")
    @Operation(
        summary = "Get JVM system properties for particular category",
        description = "Retrieves and returns the properties for a specific category, for example JVM system properties for category system ")

    public Response getPropertiesForCategory(
        @Parameter(
            description = "The category for whom to retrieve the properties for.",
            required = true,
            example = "system",
            schema = @Schema(type = SchemaType.STRING))

        @PathParam("category") String category) {
        // Get properties for category
        
        if (category.startsWith("system")) {
                Properties props = System.getProperties();
            if (props == null) {
                return Response.status(Response.Status.NOT_FOUND)
                           .entity("{ \"error\" : "
                                   + "\"Properties for category " + category + " could not be retrieved\" }")
                           .build();
            }
            return Response.ok(props).build();
        }
        
        if (category.startsWith("capital")) {
            Properties props = new Properties();
            props.put("Germany", "Berlin");
            props.put("Austria", "Wien");
            props.put("Switzerland", "Zuerich");
            props.put("France", "Paris");
            props.put("United Kingdom", "London");
            return Response.ok(props).build();
        }

        if (category.startsWith("team")) {
            Properties person1 = new Properties();
            Properties group = new Properties();
            person1.put("Surname", "Besselmann");
            person1.put("First name", "Lars");
            person1.put("Country", "Germany");
            person1.put("Skills", "Application Integration");
            group.put("User1", person1);
            
            Properties person2 = new Properties();
            person2.put("Surname", "Westphal");
            person2.put("First name", "Martin");
            person2.put("Country", "Germany");
            person2.put("Skills", "Business Automation");
            group.put("User2", person2);
            return Response.ok(group).build();
        }

       

        return Response.status(Response.Status.NOT_FOUND)
                       .entity("{ \"error\" : Category " + category + " is invalid\" }")
                           .build();
        
    }


}
