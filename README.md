# 🍃 Spring Boot Labs (DevDojo - Deep Dive)

Repositório focado no aprofundamento prático e consolidação de conceitos avançados do ecossistema Spring Boot 3+, acompanhando a série "Spring Boot Direto das Trincheiras" do DevDojo. 

O objetivo do projeto é ir além do básico, aplicando padrões de arquitetura corporativa, otimização de build, estratégia de testes e boas práticas de produção.

---

## 🎯 Tópicos e Cobertura Técnica

- [x] **Build & Code Gen:** Configuração do Maven (`annotationProcessorPaths`) garantindo interoperabilidade entre Lombok e MapStruct em tempo de compilação.
- [x] **REST API Design:** DTO Pattern, desacoplamento do modelo de domínio e padronização de contratos HTTP.
- [ ] **Data & Persistence:** Controle transacional (`@Transactional`), otimização de queries, prevenção de *N+1* e migrações de banco.
- [ ] **Exception Handling:** Tratamento global de erros padronizado via `@RestControllerAdvice` (RFC 7807 - Problem Details).
- [ ] **Testing Strategy:** Testes unitários e de integração slicados (`@WebMvcTest`, `@DataJpaTest`), Mockito e Testcontainers.
- [ ] **Security:** Spring Security, autenticação/autorização stateless com JWT e controle de acesso refinado.
- [ ] **Observabilidade & Produção:** Spring Actuator, métricas e conteinerização via Cloud Native Buildpacks.

---

## 🛠️ Stack Principal

* **Java 21 / Spring Boot 3+**
* **Lombok + MapStruct:** Mapeamento *type-safe* de alta performance via processamento de anotações no compile-time.
* **Apache Maven:** Gerenciamento de dependências e pipelines de build.
