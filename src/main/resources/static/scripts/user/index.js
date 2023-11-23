const idReg = /^[0-9a-z9A-Z]{3,20}$/;
const pwReg = /^(?=.*[a-zA-Z])(?=.*[!@#$%^&])(?=.*[0-9]).{10,30}$/;

function checkId(form) {
    let msg = "";
    if(!idReg.test(form["user-id"].value)) {
        if(form["user-id"].value.length < 3 || form["user-id"].value.length > 20) {
            msg = "아이디의 길이는 3~20으로 해주세요";
        } else {
            msg = "아이디의 영와 숫자만 포함할 수 있습니다";
        }
    }
    return msg;
}

function checkPw(form) {
    let msg = "";
    if (!pwReg.test(form["password"].value)) {
        if(form["password"].value.length < 10 || form["password"].value.length >30) {
            msg = "비밀번호의 길이는 10~30으로 해주세요";
        } else {
            msg = "비밀번호는 대소문자, 숫자, 특수문자를 포함해야 합니다";
        }
    }
    return msg;
}
