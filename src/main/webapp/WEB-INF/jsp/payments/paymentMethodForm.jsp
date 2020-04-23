<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout currentPage="payments">

    <jsp:attribute name="customScript">
        <script src="https://js.stripe.com/v3/"></script>
        <script>
            var stripe = Stripe(${apiKey});
            var elements = stripe.elements();

            var cardElement = elements.create("card");
            cardElement.mount("#card-element");

            $("#add-button").on("click", function (event) {
                event.preventDefault()


                stripe.confirmCardSetup("${intentClientSecret}", {
                    payment_method: {
                        card: cardElement
                    },
                }).then(function (result) {
                    if (result.error) {
                        alert("error processing payment method");
                    } else {
                    	console.log(result.setupIntent);
						$("#token").val(result.setupIntent.payment_method);
						$("#payment-method-form").submit();
                    };
				});
            });

        </script>
    </jsp:attribute>
    <jsp:body>

        <style>
            /**
		 * The CSS shown here will not be introduced in the Quickstart guide, but shows
		 * how you can use CSS to style your Element's container.
		 */
            .StripeElement {
                box-sizing: border-box;

                height: 40px;

                padding: 10px 12px;

                border: 1px solid transparent;
                border-radius: 4px;
                background-color: white;

                box-shadow: 0 1px 3px 0 #e6ebf1;
                -webkit-transition: box-shadow 150ms ease;
                transition: box-shadow 150ms ease;
            }

            .StripeElement--focus {
                box-shadow: 0 1px 3px 0 #cfd7df;
            }

            .StripeElement--invalid {
                border-color: #fa755a;
            }

            .StripeElement--webkit-autofill {
                background-color: #fefde5 !important;
            }

        </style>
        <div class="container">
            <h2 class="my-5">New payment method</h2>

            <form:form modelAttribute="paymentMethod" action="/payments/new-method" method="post"
                id="payment-method-form">
                <input type="hidden" name="token" id="token" />

                <label for="card-element">
                    Credit or debit card
                </label>
                <div id="card-element" style="width:100%;">
                    <!-- A Stripe Element will be inserted here. -->
                </div>

                <!-- Used to display form errors. -->
                <div id="card-errors" role="alert"></div>

                <hr />
                <button id="add-button" class="btn btn-primary">Add card</button>
            </form:form>
        </div>
    </jsp:body>
</petclinic:layout>
