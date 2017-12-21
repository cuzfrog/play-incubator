const path = require('path');
const webpack = require('webpack');
const CopyWebpackPlugin = require('copy-webpack-plugin');

const config = {
  entry: {
    main: './client/target/scala-2.12/client-fastopt.js',
    vendor: ['react', 'react-dom']
  },
  output: {
    filename: '[name].bundle.js',
    path: path.resolve(__dirname, './target') //overridden in serverSettings
  },
  module: {
    rules: [
      {test: /\.css$/, use: 'css-loader'},
      {test: /\.js$/, use: ["source-map-loader"], exclude:['https:']}
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