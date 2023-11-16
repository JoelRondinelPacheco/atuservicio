const d = document
const $rolOptions = d.getElementById('typeUser')
const $categoriesContainer = d.getElementById('categories-container')
const $closeRegisterBtn = d.getElementById('close-register-btn')
const $openRegisterBtn = d.getElementById('open-register-btn')
const $registerForm = d.getElementById('register-form')

$rolOptions.addEventListener("change", function (e) {
    let selection = e.currentTarget.value
    if (selection == 2) {
        console.log(selection)
        $categoriesContainer.style.removeProperty("display")
        $categoriesContainer.style.display = 'block';
        $registerForm.action = '/supplier/register'
    } else {
        console.log(selection)
        $categoriesContainer.style.removeProperty("display")
        $categoriesContainer.style.display = 'none';
        $registerForm.action = '/client/register'
    }
})

$closeRegisterBtn.addEventListener("click", function () {
    $categoriesContainer.style.removeProperty("display")
    $categoriesContainer.style.display = 'none';
    $registerForm.action = '/client/register'

})

$openRegisterBtn.addEventListener("click", function() {
    if ($rolOptions.value == 2) {
        console.log($rolOptions.value)
        $categoriesContainer.style.removeProperty("display")
        $categoriesContainer.style.display = 'block';
        $registerForm.action = '/supplier/register'
    }
})