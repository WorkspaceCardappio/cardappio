FROM quay.io/keycloak/keycloak:24.0.3 AS builder

ENV KC_DB=postgres
ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true

RUN /opt/keycloak/bin/kc.sh build

FROM quay.io/keycloak/keycloak:24.0.3
COPY --from=builder /opt/keycloak/ /opt/keycloak/

ENV KC_PROXY=edge
ENV KC_HTTP_ENABLED=true
ENV KC_HOSTNAME_STRICT=false

EXPOSE 8080

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]
CMD ["start", "--optimized"]
