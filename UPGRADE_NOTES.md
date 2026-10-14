# Java Version Upgrade Notes (Java 8 -> Java 25)

Scope: `finalproject/nustudy_parent` reactor and the standalone `finalproject/nustudy_parent/campus_manage` module.
Both build successfully on JDK 25 (`mvn clean install`).

## Deviations from the prescribed dependency pins

| Item | Prescribed | Applied | Reason |
| --- | --- | --- | --- |
| Spring Boot | 3.2.12 | 3.5.16 | Steering context requires upgrading to the latest LTS runtime; 3.5.x is the current supported line and is the baseline required for JDK 25. Spring Cloud 2025.0.3 / Spring Cloud Alibaba 2025.0.0.0 were aligned to it. |
| maven-compiler-plugin | 3.0.5 | 3.11.0 | The installed Maven is 3.8.4; plugin 3.13.0+ requires Maven 3.6.3+ features and newer resolver behaviour that is unavailable/unstable here, so the highest compatible release was pinned. No downgrade relative to the original build. |
| Jackson | 2.15.2 | 2.21.4 | Managed transitively by the Spring Boot 3.5.16 BOM; pinning down to 2.15.2 would conflict with the BOM and reduce security posture. No explicit override added. |
| fastjson | 1.2.x | fastjson2 2.0.65 | fastjson 1.x has unfixed critical deserialization CVEs; v2 is the maintained successor. |

## Upgrades intentionally skipped

- **Apache HttpClient — kept at 4.5.14 (not migrated to 5.x).** HttpClient 5 renames packages
  (`org.apache.http` -> `org.apache.hc.*`) and redesigns the request/response API. It is not required
  by Java 25 or Spring Boot 3.5, and 4.5.14 is the latest 4.x with no outstanding known
  vulnerabilities. Migration was deferred to avoid unrelated behavioural risk in
  `service/service_order` HTTP/SSL code.
- **Aliyun SDK (`aliyun-sdk-oss` 3.9.1, `aliyun-java-sdk-core` 4.1.1) — unchanged.** These are
  third-party cloud SDKs with no Java 25 or Jakarta incompatibility in the code paths used
  (OSS upload only). Newer majors change credential/client construction and would require
  configuration changes outside the scope of the version upgrade.
- **`javax.crypto` / `javax.net.ssl` imports retained.** These are JDK platform packages, not
  Java EE; they are explicitly out of scope for the javax -> jakarta migration.
- **No Gradle wrapper changes.** The project is Maven-only; no Gradle build files exist.

## Test status

The repository contains no executable JUnit test methods (`src/test` holds only
`TestRead`/`TestWrite`/`UserData` helpers and an empty `NustudyParentApplicationTests`).
`mvn test` reports `Tests run: 0, Failures: 0, Errors: 0` both before and after the upgrade.
All test sources were preserved.
