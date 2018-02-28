<script src="https://www.paypalobjects.com/api/checkout.js"></script>

<div id="paypal-button-container"></div>

<script>

    // Render the PayPal button

    paypal.Button.render({

        // Set your environment

        env: 'sandbox', // sandbox | production

        // Specify the style of the button

        style: {
            label: 'buynow',
            fundingicons: true, // optional
            branding: true, // optional
            size:  'small', // small | medium | large | responsive
            shape: 'rect',   // pill | rect
            color: 'gold'   // gold | blue | silve | black
        },

        // PayPal Client IDs - replace with your own
        // Create a PayPal app: https://developer.paypal.com/developer/applications/create

        client: {
            sandbox:    'AVn1vAB0az5GvMfPrBOfbAlJlBOMmUcfyumygh6wbrbN9hySVwCc2CV-g0VQnFjmY-t7aDElB08-8p0a',
            production: '<insert production client id>'
        },

        // Wait for the PayPal button to be clicked

        payment: function(data, actions) {
            return actions.payment.create({
                transactions: [
                    {
                        amount: { total: '<%=(String)session.getAttribute("prodType")%>', currency: 'USD' }
                    }
                ]
            });
        },

        // Wait for the payment to be authorized by the customer

        onAuthorize: function(data, actions) {
            return actions.payment.execute().then(function() {
                window.alert('Payment Complete!');
            });
        }

    }, '#paypal-button-container');

</script>


