$("#datepicker").datepicker({
    format: "yyyy-mm-dd",
    <!-- startDate:"-1d", -->
    endDate: "1d",
    autoclose: true,
    title: "Birth-day",
    language: "ko"
});

document.getElementById("regist-form").onsubmit = function(e) {
    
    const koreanReg = /[ㄱ-ㅎㅏ-ㅣ가-힣]/g;
    const phoneReg = /^[0-9]{3}-[0-9]{3,4}-[0-9]{4}$/;
    const emailReg = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+(.com|.net|.co.kr|.org)$/i;

    const tempName = e.target.floatingInputGroupName.value.replace(koreanReg,"aa");
    const tempPhone = e.target.floatingInputGroupPhone.value.replace(/^(\d{3})(\d{3,4})(\d{4})/,`$1-$2-$3`);

    let msg  = checkId(e.target);
    if (!msg) msg = checkPw(e.target);
    
    if (tempName.length < 4 || tempName.length > 20 ) {
        console.log(tempName.length)
        msg = "이름의 길이는 한글 2~10 영어 4~20자 기준으로 맞춰주세요"
    } else if (!phoneReg.test(tempPhone)) {
        msg = "전화번호는 형식에 맞춰서 써주세요"
    } else if(e.target.floatingInputGroupAdd.value.length > 0 &&
        (e.target.floatingInputGroupAdd.value.length < 5 || e.target.floatingInputGroupAdd.value.length > 100)) {
        msg = "주소는 5~100 자로 맞춰서 써주세요"
    } else if (!emailReg.test(e.target.floatingInputGroupEmail.value)) {
        msg = "이메일 형식에 맞춰서 써주세요"
    }

    if (msg) {
        console.log(msg);
        e.preventDefault();
    } else {
        e.target.floatingInputGroupPhone.value = tempPhone;
        console.log(e.target.floatingInputGroupGit.value);
        if (e.target.floatingInputGroupGit.value > 0 && e.target.floatingInputGroupGit.value.startWith("http://github.com/")) {
            e.target.floatingInputGroupGit.value = "https://github.com/" + e.target.floatingInputGroupGit.value;
            console.log(e.target.floatingInputGroupGit.value);
        }
    }
}

const registModal = new bootstrap.Modal(document.getElementById('myModal'))

function validateId() {
    let inputValue = document.getElementById('floatingInputGroupId').value;
    let pattern = /^[0-9a-zA-Z].{2,9}$/;
    if (inputValue != "" && !pattern.test(inputValue)) {
        document.getElementById("modalBody").innerHTML = "영어 또는 숫자,<br>3글자 ~ 10글자만 입력 가능합니다"
        registModal.show()
    }
}

function validatePw() {
    let inputValue = document.getElementById('floatingInputGroupPw').value;
    let pattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*[\!\@\#\$\%\^\&\*\(\`\)\,\.\-\_\+\=\\\|\[\]\{\}\;\:\'\"\/\?]).{10,30}$/gm;
    if (inputValue != "" && !pattern.test(inputValue)) {
        document.getElementById("modalBody").innerHTML = "대문자, 소문자, 숫자, 특수문자를 모두 포함해야 합니다<br>10자 이상 30글자 이하만 가능합니다"
        registModal.show()
    }
}

function validateName() {
    let inputValue = document.getElementById('floatingInputGroupName').value;
    let pattern = /^[가-힣]{2,10}$|^[a-zA-Z]{4,10}$/;
    if (inputValue != "" && !pattern.test(inputValue)) {
        document.getElementById("modalBody").innerHTML = "한글 최소 2 ~ 최대 10자<br>영어 최소 4 ~ 최대 20자만 가능합니다"
        registModal.show()
    }
}

function validatePhone() {
    let inputValue = document.getElementById('floatingInputGroupPhone').value;
    let pattern1 = /^[0-9]{10,11}$/;
    let pattern2 = /^(\d{3}-?\d{3,4}-?\d{4})$/;
    console.log(inputValue)
    if (inputValue.includes("-")) {
        if (inputValue != "" && !pattern2.test(inputValue)) {
            document.getElementById("modalBody").innerHTML = "전화번호 형식에 맞지 않습니다"
            registModal.show()
        }
    } else {
        if (inputValue != "" && !pattern1.test(inputValue)) {
            document.getElementById("modalBody").innerHTML = "숫자 10~11자만 입력가능합니다"
            registModal.show()
        }
    }
}

function validateAddress() {
    let inputValue = document.getElementById('floatingInputGroupAdd').value;
    let pattern = /^.{5,100}$/;
    if (inputValue != "" && !pattern.test(inputValue)) {
        document.getElementById("modalBody").innerHTML = "최소 5 ~ 최대 100자만 가능합니다"
        registModal.show()
    }
}

function validateEmail() {
    let inputValue = document.getElementById('floatingInputGroupEmail').value;
    let pattern = /^[^\s@]+@.*\.(com|org|co\.kr|net)$/;
    if (inputValue != "" && !pattern.test(inputValue)) {
        document.getElementById("modalBody").innerHTML = "@가 포함되어야 합니다<br>.com .org .co.kr .net 으로 끝나야만 합니다"
        registModal.show()
    }
}

// document.getElementById('submitButton').addEventListener("click", () => {
//     // 깃허브 주소 추가
//     let gitValue = document.getElementById('floatingInputGroupGit').value;
//     if (gitValue != "" && gitValue.indexOf("/") === -1) {
//         gitValue = "https://github.com/" + gitValue;
//         document.getElementById('floatingInputGroupGit').value = gitValue;
//     }
                    
//     //폰번호 하이픈 추가
//     let phoneValue = document.getElementById('floatingInputGroupPhone').value;
//     if (phoneValue.length === 10) {
//         phoneValue = `${phoneValue.slice(0, 3)}-${phoneValue.slice(3, 6)}-${phoneValue.slice(6)}`;
//         document.getElementById('floatingInputGroupPhone').value = phoneValue
//         console.log(document.getElementById('floatingInputGroupPhone').value)
//     } else if (phoneValue.length === 11) {
//         phoneValue = `${phoneValue.slice(0, 3)}-${phoneValue.slice(3, 7)}-${phoneValue.slice(7)}`;
//         document.getElementById('floatingInputGroupPhone').value = phoneValue
//         console.log(document.getElementById('floatingInputGroupPhone').value)
//     }
// })

