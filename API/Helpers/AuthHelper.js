const jwt = require('jsonwebtoken');
const httpStatusCode = require('http-status-codes');

const dbConfig = require('../Config/secret');

module.exports = {
  VerifyToken: (req,res,next) => {
    if(!req.headers.authorization){
      return res.status(httpStatusCode.UNAUTHORIZED).json({ Message: 'Authorization Error'});
    }
    const token= req.cookies.auth || req.headers.authorization.split(' ')[1];

    if(!token){
      return res.status(httpStatusCode.FORBIDDEN).json({Message: 'No Token Provided'});
    }
    return jwt.verify(token, dbConfig.secret, (err, decoded) => {
      if(err){
        if(err.expiredAt < new Date()){
          return res.status(httpStatusCode.INTERNAL_SERVER_ERROR).json({Message: 'Token has expired, please login again', token: null});
        }
        next();
      }
      req.user = decoded.data;
      next();
    });
  }
}