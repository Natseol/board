document.getElementById("login-form").onsubmit = (e) => {
    
    let msg = checkId(e.target);
    
    if (!msg) {
        msg = checkPw(e.target);
    }
    if (msg) {
        alert(msg);
        // e.preventDefault();
    }
};
