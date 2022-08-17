//Функции
function agreeWithTerms() {
    let form = this.parentNode.querySelector(".form_user_agreement")
    if(this.checked) {
        form.querySelector("input").removeAttribute("disabled")
    } else {
        form.querySelector("input").disabled
    }
}
//Выполнение кода
document.querySelector('input[type=checkbox]').addEventListener("change", agreeWithTerms)