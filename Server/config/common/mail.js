var nodemailer = require("nodemailer");
const transporter = nodemailer.createTransport({
    service:"gmail",
    auth:{
        user: "quynhltph31990@fpt.edu.vn",
        pass: "gmjp iuwd npgn hfgg"
    },
});
module.exports = transporter