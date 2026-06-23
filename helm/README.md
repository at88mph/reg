# reg

IVOA Registry Service Helm Chart

| Chart | AppVersion | Type |
|:-----:|:----------:|:----:|
|1.2.0<!-- x-release-please-version --> | 1.2.1 | application |

## Values

| Key | Type | Default | Description |
|-----|------|---------|-------------|
| affinity | object | `{}` |  |
| application.logging | string | `nil` |  |
| application.oaiPublishing.authority | string | `""` |  |
| application.oaiPublishing.enabled | bool | `false` |  |
| application.serviceEntries | list | `[]` |  |
| application.vosi | string | `nil` |  |
| autoscaling.enabled | bool | `false` |  |
| autoscaling.maxReplicas | int | `100` |  |
| autoscaling.minReplicas | int | `1` |  |
| autoscaling.targetCPUUtilizationPercentage | int | `80` |  |
| fullnameOverride | string | `""` |  |
| httpRoute | object | `{"annotations":{},"enabled":false,"hostnames":["chart-example.local"],"parentRefs":[{"name":"gateway","sectionName":"http"}],"rules":[{"matches":[{"path":{"type":"PathPrefix","value":"/headers"}}]}]}` | Expose the service via gateway-api HTTPRoute Requires Gateway API resources and suitable controller installed within the cluster (see: https://gateway-api.sigs.k8s.io/guides/) |
| image.pullPolicy | string | `"IfNotPresent"` |  |
| image.repository | string | `"images.opencadc.org/core/reg"` |  |
| image.tag | string | `"1.2.1"` |  |
| imagePullSecrets | list | `[]` |  |
| ingress.annotations | object | `{}` |  |
| ingress.className | string | `""` |  |
| ingress.enabled | bool | `false` |  |
| ingress.hosts | list | `[]` |  |
| ingress.tls | list | `[]` |  |
| livenessProbe | string | `nil` |  |
| nameOverride | string | `""` |  |
| nodeSelector | object | `{}` |  |
| podAnnotations | object | `{}` |  |
| podLabels | object | `{}` |  |
| podSecurityContext | object | `{}` |  |
| readinessProbe | string | `nil` |  |
| replicaCount | int | `1` |  |
| resources | object | `{}` |  |
| securityContext | object | `{}` |  |
| service.port | int | `8080` |  |
| service.type | string | `"ClusterIP"` |  |
| serviceAccount.annotations | object | `{}` |  |
| serviceAccount.automount | bool | `true` |  |
| serviceAccount.create | bool | `true` |  |
| serviceAccount.name | string | `""` |  |
| tolerations | list | `[]` |  |
| tomcat.connector | object | `{"proxyName":"","proxyPort":"","scheme":""}` | Public URL for Tomcat behind a reverse proxy. Written to catalina.properties as tomcat.connector.* (X-Forwarded-Proto/Host/Port equivalent). Affects VOSI accessURL hostnames. Auto-derived from httpRoute or ingress when fields are empty; omitted for internal-only ClusterIP deployments. |
| tomcat.connector.proxyName | string | `""` | Public URL hostname. Auto-derived from httpRoute.hostnames[0] or ingress.hosts[0].host. |
| tomcat.connector.proxyPort | string | `""` | Public URL port. Auto-derived as 443 (https) or 80 (http). |
| tomcat.connector.scheme | string | `""` | Public URL scheme. Auto-derived as https (httpRoute or ingress with TLS) or http. |
| volumeMounts | list | `[]` |  |
| volumes | list | `[]` |  |
