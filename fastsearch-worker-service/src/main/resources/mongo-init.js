db = db.getSiblingDB('db')

db.createCollection('docs')

db.docs.createIndex(
  { title: "text", text: "text" },
  { name: "TextIndex" }
)
