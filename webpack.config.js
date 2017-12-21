const path = require('path');
const webpack = require('webpack');
const CopyWebpackPlugin = require('copy-webpack-plugin');

const config = {
  entry: {
    vendor: ['react', 'react-dom']
  },
  output: {
    filename: '[name].bundle.js'
    //path: path.resolve(__dirname, clientPath) //defined in serverSettings
  },
  module: {
    rules: [
      {test: /\.css$/, use: 'css-loader' }
    ]
  },
  plugins: [
    new CopyWebpackPlugin([
      {from: 'node_modules/bulma/css', to: 'css/'},
      {from: 'node_modules/font-awesome/css', to: 'css/'},
      {from: 'node_modules/font-awesome/fonts', to: 'fonts/'},
    ])
  ]
};

module.exports = config;