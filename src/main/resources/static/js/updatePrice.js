document.addEventListener('DOMContentLoaded', function() {
    updateTotalPrice();
});

function count(button, type) {
    const index = button.getAttribute("data-index");
    const resultElement = document.querySelector(`#result-${index}`);
    if (!resultElement) {
        console.error(`Element with id result-${index} not found`);
        return;
    }

    let number = parseInt(resultElement.value);
    const minValue = parseInt(resultElement.min);
    const maxValue = parseInt(resultElement.max);

    if (type === "plus" && number < maxValue) {
        number++;
    } else if (type === "minus" && number > minValue) {
        number--;
    }
    resultElement.value = number;

    updatePrice(index, number);
}

function updatePrice(index, quantity){
    const priceElement = document.querySelector(`#price-${index}`);
    const pricePerItem = parseInt(document.querySelector(`#hidden-price-${index}`).value);

    if (priceElement) {
        priceElement.textContent = '₩' + (pricePerItem * quantity).toLocaleString();
        console.log('pricePerItem : '+ pricePerItem);
    } else {
        console.error(`Element with id price-${index} not found`);
    }

    updateTotalPrice()
}

function updateTotalPrice() {
    const allPriceElements = document.querySelectorAll('.item-price');  // 모든 항목 가격 요소 선택
    let totalPrice = 0;

    // 각 항목 가격을 더함
    allPriceElements.forEach(priceElement => {
        const priceText = priceElement.textContent;
        // ₩ 기호를 제거하고 숫자만 추출
        const price = parseInt(priceText.replace('₩', '').replace(',', ''));

        if (!isNaN(price)) {
            totalPrice += price;
        }
    });

    const paidAmount = totalPrice + 5000;

    // 총합을 화면에 출력
    const totalPriceElement = document.querySelector('#total-price');  // 전체 가격을 출력할 요소
    if (totalPriceElement) {
        totalPriceElement.textContent = '₩' + totalPrice.toLocaleString();  // 총합을 표시 (콤마 포함)
    } else {
        console.error('Total price element not found');
    }

    const paidAmountElement = document.querySelector('#paid-amount');
    if (paidAmountElement) {
        paidAmountElement.textContent = '₩' + paidAmount.toLocaleString();  // 결제 금액 표시 (콤마 포함)
    } else {
        console.error('Paid amount element not found');
    }
}
