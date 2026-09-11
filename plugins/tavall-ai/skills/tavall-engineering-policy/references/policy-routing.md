# Policy Routing Reference

Do not maintain a second concern-to-document map in Tavall AI.

The canonical routing table is the current `main` branch file:

`TavallStudios/tavall-docs/docs/quality/DOCUMENT_ROUTING.yml`

`tavall-engineering-policy` identifies concrete engineering concerns. `tavall-documentation-context` resolves those concerns against that live routing table and reads only the selected documents.

Use this local reference only to preserve that ownership boundary. If a signal or document mapping needs to change, change the canonical routing table in `tavall-docs`; do not add a competing mapping here.

Selection precedence remains:

1. explicit user document path/name;
2. exact user-prompt signal;
3. exact agent-reasoning concern label;
4. targeted current-document search when unresolved.

There is no recursive-read fallback.
