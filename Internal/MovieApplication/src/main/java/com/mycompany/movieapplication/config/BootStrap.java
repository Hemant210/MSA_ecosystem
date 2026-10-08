package com.mycompany.movieapplication.config;
import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import org.eclipse.microprofile.auth.LoginConfig;


@LoginConfig(authMethod = "MP-JWT")
@DeclareRoles({"MOVIEUSER", "admin"})
@SuppressWarnings({"EmptyClass", "SuppressionAnnotation"})
@ApplicationPath("rest")
public class BootStrap extends jakarta.ws.rs.core.Application {
}
