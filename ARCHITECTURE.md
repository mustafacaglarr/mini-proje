## Mini Proje Mimarisi

- **Teknolojiler**: Spring Boot 3 (Web/WebFlux + Gateway), in-memory repository’ler (HashMap/Set), RestTemplate ile servisler arası basit çağrı. Docker yok; odak akış ve ayrışma.
- **Servisler**:
  - `auth-service` (8081): Mock kullanıcı/şifre/rol listesi, `/auth/login` ile rol bilgisi ve mock token döner.
  - `course-service` (8082): Eğitim listesi (mock JSON), `/courses/purchase` ile ödeme servisini çağırarak satın alma, `/courses/purchased/{userId}` ile kullanıcının satın aldıkları.
  - `payment-service` (8083): `/payment/pay` sahte ödeme, her zaman başarılı, transactionId üretir.
  - `matching-service` (8084): `/match/request` ile canlı ders talebi, en uygun (ilk) eğitmeni atar, bildirim simüle eder.
  - `gateway-service` (8080): Spring Cloud Gateway ile `/api/**` prefiksi üzerinden yukarıdaki servisleri ters proxy eder (StripPrefix=1).

### Ödeme Akışı
1. İstemci gateway üzerinden `/api/courses/purchase` çağırır (userId, courseId).
2. course-service ilgili kursu bulur, ödeme tutarını hesaplar.
3. payment-service `/payment/pay` çağrılır; başarı dönerse enrollment kaydı (in-memory) güncellenir.
4. Yanıtta satın alınan kurs ve kullanıcının tüm satın aldıkları gönderilir.

### Eşleştirme Mantığı
- matching-service statik eğitmen listesinden ilk uygun eğitmeni atar (`getAvailable`), requestId üretir, bildirim mesajı döner. İstenirse ileride skor/availability/queue eklenebilir.

### Ölçeklenebilirlik / Gelecek Adımlar
- Servisleri konteynerize edip API Gateway + Service Discovery (Eureka/Consul) eklenebilir.
- Kimlik doğrulama için JWT/OAuth2, gateway’de rol tabanlı filtreler eklenebilir.
- Payment idempotency + retry + message queue (Kafka/RabbitMQ) ile sağlamlık artırılabilir.
- Matching için gerçek zamanlı queue, eğitmen durumu, bölge/puan bazlı seçim ve WebSocket/SSE bildirimleri eklenebilir.
