# Adapter — Payments (Refactoring)

## Narrative (Current Code)
OrderService directly depends on two mismatched SDKs (`FastPayClient`, `SafeCashClient`), uses a string `provider` switch, and duplicates glue logic.

## Your Task
Introduce an **Adapter** so `OrderService` depends only on a `PaymentGateway` interface. Create:
- `PaymentGateway` (target interface): `String charge(String customerId, int amountCents)`
- `FastPayAdapter` and `SafeCashAdapter` mapping to their respective SDKs
- A simple map-based registry in `App` to select the gateway

Refactor `OrderService` to accept a `PaymentGateway` and remove provider branching.

## Acceptance Criteria
- `OrderService` calls **only** `PaymentGateway`
- Adding a new provider requires no change to `OrderService`
- Running `App` prints transaction IDs for both providers

## Hints
- Use constructor injection or a `Map<String, PaymentGateway>`
- Keep adapters stateless
- Use `Objects.requireNonNull` to validate inputs

## Build & Run
```bash
cd adapter-payments/src
javac com/example/payments/*.java
java com.example.payments.App
```

---

**Step 1 — State the problem:**
"OrderService needs to charge payments through two SDKs — FastPayClient and SafeCashClient. The problem is they have completely different APIs. FastPayClient has payNow(custId, amount). SafeCashClient has a two-step process: createPayment(amount, user) which returns a SafeCashPayment object, then you call payment.confirm(). Even the parameter order is different."

**Step 2 — Explain the Adapter concept:**
"It's like a travel power adapter. Your Indian charger has a round-pin plug, the UK socket has rectangular holes. The adapter doesn't change how either works — it just translates between them. That's what we did here."

**Step 3 — Show PaymentGateway interface:**
"We defined one common interface: PaymentGateway with charge(customerId, amountCents). This is the 'standard socket' that OrderService plugs into."

**Step 4 — Show FastPayAdapter.java:**
"FastPayAdapter implements PaymentGateway. Its charge() method just calls client.payNow() — a simple 1:1 delegation since the APIs almost match."

**Step 5 — Show SafeCashAdapter.java:**
"SafeCashAdapter is more interesting. Its charge() method handles the two-step process and the reversed parameter order: it calls client.createPayment(amountCents, customerId), then payment.confirm(). The adapter hides this complexity from OrderService."

**Step 6 — Show OrderService.java:**
"OrderService now accepts a Map of PaymentGateway instances. When charge() is called with a provider name, it looks up the gateway and calls gw.charge(). It has zero knowledge of FastPayClient or SafeCashClient. Adding a third provider like Stripe means writing one StripeAdapter — zero changes to OrderService."

**Step 7 — Show App.java:**
"App registers the adapters: gateways.put('fastpay', new FastPayAdapter(new FastPayClient())). This is where we wire the adapters to the real SDKs."